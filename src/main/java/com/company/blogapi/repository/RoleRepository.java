package com.company.blogapi.repository;


import com.company.blogapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Role findRoleById(Long roleId);
}
