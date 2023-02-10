package com.company.blogapi.dto.request;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String emailorusername;
    private String password;
}
