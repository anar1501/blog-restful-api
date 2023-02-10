package com.company.blogapi.service;

import com.company.blogapi.config.ModelMapperConfiguration;
import com.company.blogapi.dto.response.PermissionResponseDto;
import com.company.blogapi.dto.response.RoleResponseDto;
import com.company.blogapi.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public record RoleService(RoleRepository roleRepository){

    public List<RoleResponseDto> findAll() {
        return roleRepository.findAll().stream().map((role) -> ModelMapperConfiguration.map(role, RoleResponseDto.class)).collect(Collectors.toList());
    }

    public List<PermissionResponseDto> findPermissionsOfRole(Long roleId) {
        try {
            return roleRepository.findRoleById(roleId).getPermissions().stream().map((permission) -> ModelMapperConfiguration.map(permission, PermissionResponseDto.class)).collect(Collectors.toList());
        } catch (Exception exception) {
            log.error("EXCEPTION OCCURED IN findPermissionsOfRole METHOD {}",exception.getMessage());
        }
        return new ArrayList<>();
    }

}
