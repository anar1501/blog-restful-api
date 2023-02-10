package com.company.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class PostResponseDto {

    @ApiModelProperty(value = "Blog post id")
    private Long id;

    @ApiModelProperty(value = "Blog post content")
    private String content;

    @ApiModelProperty(value = "Blog post title")
    private String title;

    @ApiModelProperty(value = "Blog post description")
    private String description;

    @ApiModelProperty(value = "Blog post comments")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<CommentResponseDto> comments;

}
