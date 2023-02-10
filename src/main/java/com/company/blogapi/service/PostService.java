package com.company.blogapi.service;


import com.company.blogapi.dto.request.PostRequestDto;
import com.company.blogapi.dto.response.PaginationInfoPostResponseDto;
import com.company.blogapi.dto.response.PostResponseDto;
import com.company.blogapi.mapper.PostMapper;
import com.company.blogapi.mapper.PostMapperUtility;
import com.company.blogapi.model.Post;
import com.company.blogapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public record PostService(PostRepository postRepository,
                          PostMapperUtility postMapperUtility,
                          PostMapper postMapper) {

    @Transactional
    public PaginationInfoPostResponseDto getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortType) {
        Sort sort = sortType.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postRepositoryAll = postRepository.findAll(pageable);
        List<Post> postList = postRepositoryAll.getContent();
        return postMapperUtility.preparePaginationInfoPostResponseDto(postList, postRepositoryAll);
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        return postMapper.map(postRepository.save(postMapper.map(requestDto)));
    }

    public PostResponseDto getById(Long id) {
        return postMapper.map(postRepository.getOne(id));
    }

    public PostResponseDto updateById(Long id, PostRequestDto postRequestDto) {
        return postMapper.map(postRepository.save(postMapper.map(postRequestDto, postRepository.getOne(id))));
    }

    public void deleteById(Long id) {
        postRepository.delete(postRepository.getOne(id));
    }
}
