package com.company.blogapi.repository;


import com.company.blogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByEmailorusername(String username);
    Optional<User> findUserByEmailorusername(String emailOrUsername);
}
