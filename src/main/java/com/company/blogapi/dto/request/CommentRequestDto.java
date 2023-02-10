package com.company.blogapi.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {

    @ApiModelProperty(value = "Comment name")
    @NotEmpty
    @Size(min = 2, message = "Name should not be null or empty")
    private String name;

    @ApiModelProperty(value = "Comment email")
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @ApiModelProperty(value = "Comment body")
    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;
}
