package com.company.blogapi.mapper;

import com.company.blogapi.dto.request.CommentRequestDto;
import com.company.blogapi.dto.response.CommentResponseDto;
import com.company.blogapi.model.Comment;
import com.company.blogapi.repository.CommentRepository;
import com.company.blogapi.repository.PostRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), imports = {Objects.class})
public abstract class CommentMapper {
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;
    public abstract Comment map(CommentRequestDto requestDto);
    public abstract CommentResponseDto map(Comment comment);

    @AfterMapping
    void mapAfter(@MappingTarget Comment comment,Long id){
        comment.setPost(postRepository.getOne(id));
        commentRepository.save(comment);
    }
}
