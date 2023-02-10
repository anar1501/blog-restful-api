package com.company.blogapi.dto.request;


import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class PostRequestDto {

    @ApiModelProperty(value = "Blog post conent")
    @NotEmpty
    @Size(min = 2, message = "Content should have at least 2 characters")
    private String content;

    @ApiModelProperty(value = "Blog post title")
    @NotEmpty
    @Size(min = 5, message = "Title should have at least 5 characters")
    private String title;
    @NotEmpty
    private String description;
}
