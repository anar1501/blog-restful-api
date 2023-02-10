package com.company.blogapi.dto.request;

import lombok.Data;

import java.io.Serializable;

@SuppressWarnings(value = "ALL")
@Data
public record ResetPasswordRequestDto(String sixDigitCode,
        String newPassword) implements Serializable {}

