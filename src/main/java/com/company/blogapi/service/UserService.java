package com.company.blogapi.service;

import com.company.blogapi.config.ModelMapperConfiguration;
import com.company.blogapi.dto.request.LoginRequestDto;
import com.company.blogapi.dto.request.RegisterConfirmRequestDto;
import com.company.blogapi.dto.request.RegisterRequestDto;
import com.company.blogapi.dto.request.ResetPasswordRequestDto;
import com.company.blogapi.dto.response.JWTAuthResponse;
import com.company.blogapi.dto.response.UserResponseDto;
import com.company.blogapi.enums.UserStatusEnum;
import com.company.blogapi.exception.*;
import com.company.blogapi.mapper.UserMapper;
import com.company.blogapi.model.Role;
import com.company.blogapi.model.User;
import com.company.blogapi.repository.RoleRepository;
import com.company.blogapi.repository.UserRepository;
import com.company.blogapi.repository.UserStatusRepository;
import com.company.blogapi.security.jwt.JwtUtil;
import com.company.blogapi.utils.DateUtils;
import com.company.blogapi.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public record UserService(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil tokenProvider,
                          MessageUtils messageUtils,
                          UserMapper userMapper,
                          JwtUtil jwtUtil,
                          UserStatusRepository userStatusRepository) {

    private final static Date currentDate = new Date();
    @Value("${my.message.subject}")
    private static String messageSubject;

    @Value("${my.message.body}")
    private static String messageBody;
    @Value("${my.message.forget-subject}")
    private static String forgetMessageSubject;

    @Value("${my.message.forget-body}")
    private static String forgetMessageBody;

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
        messageUtils.sendAsync(savedUser.getEmailorusername(), messageSubject, messageBody + "http://localhost:8080/register-confirm?code=" + savedUser.getActivationCode());
    }


    @Transactional
    public void registerConfirm(RegisterConfirmRequestDto requestDto) {
        User user = userRepository.findUserByActivationCode(requestDto.getSixDigitCode());
        if (user.getStatus().getId().equals(UserStatusEnum.CONFIRMED.getStatusId())) {
            throw new AlreadyConfirmedException();
        }
        Date expiredDate = user.getExpiredDate();
        if (expiredDate.before(currentDate)) {
            throw new ExpirationCodeIsExpiredException();
        } else {
            user.setStatus(userStatusRepository.findUserStatusByStatusId(UserStatusEnum.CONFIRMED.getStatusId()));
            userRepository.save(user);
        }
    }

    public void resendEmail(Long id) {
        User user = userRepository.getOne(id);
        user.setActivationCode(DateUtils.getRandomNumberString());
        user.setExpiredDate(DateUtils.prepareRegistrationExpirationDate());
        User saveUser = userRepository.save(user);
        messageUtils.sendAsync(saveUser.getEmailorusername(), messageSubject, messageBody + "http://localhost:8080/register-confirm?code=" + saveUser.getActivationCode());
    }

    public void forgetPassword(String email) {
        User user = userRepository.findUserByEmailorusername(email).orElseThrow(EmailIsInCorrectException::new);
        user.setSixDigitCode(DateUtils.getRandomNumberString());
        user.setForgetPasswordExpiredDate(DateUtils.prepareForgetPasswordExpirationDate());
        User saveUser = userRepository.save(user);
        messageUtils.sendAsync(saveUser.getEmailorusername(), forgetMessageSubject, forgetMessageBody + saveUser.getSixDigitCode());
    }

    public void resetPassword(ResetPasswordRequestDto requestDto) {
        User user = userRepository.findBySixDigitCode(requestDto.getSixDigitCode()).orElseThrow(SixDigitCodeInCorrectException::new);
        Date expiredDate = user.getForgetPasswordExpiredDate();
        if (expiredDate.before(currentDate)) {
            throw new ExpirationCodeIsExpiredException();
        }
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> showUsersExpectAdmin() {
        List<User> userList = userRepository.findUsersByRoleIdNotLike(2L);
        UserResponseDto userResponseDto = new UserResponseDto();
        userList.forEach((user) -> {
            userResponseDto.setRoleName(user.getRoles().stream().findAny().get().getName().substring(user.getRoles().stream().findAny().get().getName().lastIndexOf("_") + 1));
            userResponseDto.setStatusName(user.getStatus().getName());
        });
        return userList.stream().map(user -> ModelMapperConfiguration.map(user, userResponseDto)).collect(Collectors.toList());
    }

    public UserResponseDto findUserRole(Long id) {
        UserResponseDto responseDto = new UserResponseDto();
        try {
            User user = userRepository.findUserByUserId(id);
            responseDto.setRoleName(user.getRoles().stream().findAny().get().getName().substring(user.getRoles().stream().findAny().get().getName().lastIndexOf("_") + 1));
        } catch (Exception ex) {
            log.error("exception occured IN FIND-USER-ROLE-METHOD {}", ex.getMessage());
        }
        return responseDto;
    }


    public void updateUserRoleByUserAndRoleId(Long userId, Long roleId) {
        try {
            User user = userRepository.findUserByUserId(userId);
            Role role = roleRepository.findRoleById(roleId);
            user.setRoles(Collections.singleton(role));
            userRepository.save(user);
        } catch (Exception ex) {
            log.error("exception occured IN UPDATE-USER-ROLE-BY-USER-AND-ROLE-ID {}", ex.getMessage());
        }
    }
}
