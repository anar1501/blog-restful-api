package com.company.blogapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionResponseDto implements Serializable {
    private Long permissionId;
    private String name;
}
