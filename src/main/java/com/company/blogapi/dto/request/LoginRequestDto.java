package com.company.blogapi.dto.request;

import lombok.Data;

@SuppressWarnings(value = "ALL")
@Data
public record LoginRequestDto(String emailorusername, String password) {}
