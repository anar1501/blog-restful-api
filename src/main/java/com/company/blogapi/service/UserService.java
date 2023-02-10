package com.company.blogapi.service;

import com.company.blogapi.dto.request.LoginRequestDto;
import com.company.blogapi.dto.request.RegisterRequestDto;
import com.company.blogapi.dto.response.JWTAuthResponse;
import com.company.blogapi.enums.UserStatusEnum;
import com.company.blogapi.exception.*;
import com.company.blogapi.mapper.UserMapper;
import com.company.blogapi.model.User;
import com.company.blogapi.repository.RoleRepository;
import com.company.blogapi.repository.UserRepository;
import com.company.blogapi.security.jwt.JwtUtil;
import com.company.blogapi.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public record UserService(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil tokenProvider,
                          MessageUtils messageUtils,
                          UserMapper userMapper,
                          JwtUtil jwtUtil) {


    @Value("${my.message.subject}")
    private static String messageSubject;

    @Value("${my.message.body}")
    private static String messageBody;

    @Transactional
    public JWTAuthResponse login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findUserByEmailorusername(loginRequestDto.getEmailorusername())
                .orElseThrow(EmailOrUsernameNotFoundException::new);
        boolean isMatches = passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!isMatches) {
            throw new WrongPasswordException();
        }
        boolean isConfirmed = user.getStatus().getId().equals(UserStatusEnum.UNCONFIRMED.getStatusId());
        if (isConfirmed) {
            throw new UnconfirmedException();
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmailorusername(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JWTAuthResponse(jwtUtil.generateToken(authentication));
    }

    @Transactional
    public void register(RegisterRequestDto registerRequestDto) {
        Boolean existsByUsername = userRepository.existsByEmailorusername(registerRequestDto.getEmailorusername());
        if (existsByUsername) {
            throw new UsernameAlreadyTakenException();
        }
        Boolean existsByEmail = userRepository.existsByEmailorusername(registerRequestDto.getEmailorusername());
        if (existsByEmail) {
            throw new EmailAlreadyTakenException();
        } else if (!registerRequestDto.getPassword().equals(registerRequestDto.getRepeatPassword())) {
            throw new PasswordRepeatNotSameException();
        }
        User mappedUser = userMapper.map(registerRequestDto);
        User savedUser = userRepository.save(mappedUser);
        messageUtils.sendAsync(savedUser.getEmailorusername(),messageSubject,messageBody+"http://localhost:8080/register-confirm?code="+savedUser.getActivationCode());
    }


}
