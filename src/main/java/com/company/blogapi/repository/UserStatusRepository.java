package com.company.blogapi.repository;

import com.company.blogapi.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    UserStatus findUserStatusByStatusId(Long id);
}
