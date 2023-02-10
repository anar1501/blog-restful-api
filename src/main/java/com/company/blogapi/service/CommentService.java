package com.company.blogapi.service;


import com.company.blogapi.dto.request.CommentRequestDto;
import com.company.blogapi.dto.response.CommentResponseDto;
import com.company.blogapi.mapper.CommentMapper;
import com.company.blogapi.model.Comment;
import com.company.blogapi.model.Post;
import com.company.blogapi.repository.CommentRepository;
import com.company.blogapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public record CommentService(
        PostRepository postRepository,
        CommentRepository commentRepository,
        CommentMapper commentMapper){

    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.map(commentRequestDto);
        comment.setPost(postRepository.getOne(postId));
        return commentMapper.map(comment);
    }

    public List<CommentResponseDto> getAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId).stream().map(commentMapper::map).collect(Collectors.toList());
    }

    public CommentResponseDto getByPostAndCommentId(Long postId, Long commentId) {
        Comment comment = getComment(postId, commentId);
        return commentMapper.map(comment);
    }

    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto) {
        return commentMapper.map(commentRepository.save(commentMapper.map(requestDto)));
    }

    public void deleteComment(Long postId, Long commentId) {
        commentRepository.delete(getComment(postId, commentId));
    }

    private Comment getComment(Long postId, Long commentId) {
        Post post = postRepository.getOne(postId);
        Comment comment = commentRepository.getOne(commentId);
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new EntityNotFoundException();
        }
        return comment;
    }
}
