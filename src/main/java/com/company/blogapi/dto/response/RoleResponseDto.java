package com.company.blogapi.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleResponseDto implements Serializable {
    private Long roleId;
    private String name;
}
