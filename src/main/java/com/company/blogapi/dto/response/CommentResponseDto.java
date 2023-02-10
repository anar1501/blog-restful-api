package com.company.blogapi.dto.response;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
