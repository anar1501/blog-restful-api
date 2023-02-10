package com.company.blogapi.repository;


import com.company.blogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByEmailorusername(String username);
    Optional<User> findUserByEmailorusername(String emailOrUsername);
    Optional<User> findBySixDigitCode(String sixDigitCode);
    User findUserByActivationCode(String sixDigitCode);
    List<User> findUsersByRoleIdNotLike(Long roleId);
    User findUserByUserId(Long id);
}
