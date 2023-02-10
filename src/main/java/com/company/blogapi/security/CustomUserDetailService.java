package com.company.blogapi.security;

import com.company.blogapi.exception.EmailOrUsernameNotFoundException;
import com.company.blogapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return new UserPrincipal(userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(EmailOrUsernameNotFoundException::new));
    }

}
