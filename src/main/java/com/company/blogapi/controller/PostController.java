package com.company.blogapi.controller;

import com.company.blogapi.dto.request.PostRequestDto;
import com.company.blogapi.dto.response.PaginationInfoPostResponseDto;
import com.company.blogapi.dto.response.PostResponseDto;
import com.company.blogapi.service.PostService;
import com.company.blogapi.utils.ConstraintUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "CRUD Rest APIs for Post resources")
@RestController
public record PostController(PostService postService) {

    @ApiOperation(value = "Create Post REST API")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        return new ResponseEntity<>(postService.createPost(postRequestDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Posts REST API")
    @GetMapping("/api/v1/posts")
    public ResponseEntity<PaginationInfoPostResponseDto> getAll
            (
                    @RequestParam(value = "pageNumber", defaultValue = ConstraintUtil.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                    @RequestParam(value = "pageSize", defaultValue = ConstraintUtil.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                    @RequestParam(value = "sortBy", defaultValue = ConstraintUtil.DEFAULT_SORT_VALUE, required = false) String sortBy,
                    @RequestParam(value = "sortType", defaultValue = ConstraintUtil.DEFAULT_SORT_TYPE, required = false) String sortType

            ) {
        return ResponseEntity.ok(postService.getAll(pageNumber, pageSize, sortBy, sortType));
    }

    @ApiOperation(value = "Get Post By Id REST API")
    @GetMapping(value = "/api/v1/posts/{id}")
    public ResponseEntity<PostResponseDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @ApiOperation(value = "Update Post By Id REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostResponseDto> updateById(@PathVariable("id") Long id, @Valid @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.updateById(id, postRequestDto));
    }

    @ApiOperation(value = "Delete Post By Id REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public HttpStatus deleteById(@PathVariable("id") Long id) {
        postService.deleteById(id);
        return HttpStatus.OK;
    }
}
