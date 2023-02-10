package com.company.blogapi.mapper;

import com.company.blogapi.dto.request.PostRequestDto;
import com.company.blogapi.dto.response.PostResponseDto;
import com.company.blogapi.model.Post;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.Objects;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), imports = {Objects.class})
public abstract class PostMapper {
    public abstract PostResponseDto map(Post post);
    public abstract Post map(PostRequestDto requestDto);

    public abstract Post map(PostRequestDto postRequestDto, Post one);
}
