package com.company.blogapi.controller;

import com.company.blogapi.dto.request.CommentRequestDto;
import com.company.blogapi.dto.response.CommentResponseDto;
import com.company.blogapi.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD REST APIs for Comment Respource")
@RestController
@RequestMapping("/api")
public record CommentController(CommentService commentService) {

    @ApiOperation(value = "Create Comment REST API")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentRequestDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Comments By Post ID REST API")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(commentService.getAllByPostId(postId));
    }

    @ApiOperation(value = "Get Single Comment By ID REST API")
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getByPostAndCommentId(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getByPostAndCommentId(postId, commentId));
    }

    @ApiOperation(value = "Update Comment By ID REST API")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,@Valid @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, requestDto));
    }

    @ApiOperation(value = "Delete Comment By ID REST API")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted succesfully", HttpStatus.OK);
    }
}
