package com.company.blogapi.controller;

import com.company.blogapi.dto.response.PermissionResponseDto;
import com.company.blogapi.dto.response.RoleResponseDto;
import com.company.blogapi.dto.response.UserResponseDto;
import com.company.blogapi.service.RoleService;
import com.company.blogapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "api/v1/admin")
public record AdminController(UserService userService,
                              RoleService roleService) {

    //    @PreAuthorize("hasAuthority('read')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> showUsersExceptAdmin() {
        return ResponseEntity.ok(userService.showUsersExpectAdmin());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponseDto> findUserRoleById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(userService.findUserRole(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "{id}/{roleId}")
    public HttpStatus updateUserRoleUserAndRoleId(@PathVariable(value = "id") Long id, @PathVariable(value = "roleId") Long roleId) {
        userService.updateUserRoleByUserAndRoleId(id, roleId);
        return HttpStatus.OK;
    }

    @GetMapping(value = "roles")
    public ResponseEntity<List<RoleResponseDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping(value = "role/{id}/permissions")
    public ResponseEntity<List<PermissionResponseDto>> findPermissionsOfRole(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(roleService.findPermissionsOfRole(id));
    }

}
