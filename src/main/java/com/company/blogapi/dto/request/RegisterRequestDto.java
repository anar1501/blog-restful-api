package com.company.blogapi.dto.request;

import lombok.Data;

@SuppressWarnings(value = "ALL")
@Data
public record RegisterRequestDto(String emailorusername,
                                 String password,
                                 String repeatPassword) {

}
