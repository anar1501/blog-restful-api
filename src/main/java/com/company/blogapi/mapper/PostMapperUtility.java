package com.company.blogapi.mapper;


import com.company.blogapi.dto.response.PaginationInfoPostResponseDto;
import com.company.blogapi.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class PostMapperUtility {

    private final PostMapper postMapper;

    public PaginationInfoPostResponseDto preparePaginationInfoPostResponseDto(List<Post> postList, Page<Post> postPage) {
        PaginationInfoPostResponseDto returnValue = new PaginationInfoPostResponseDto();
        returnValue.setContent(postList.stream().map(postMapper::map).collect(Collectors.toList()));
        returnValue.setPageNumber(postPage.getNumber());
        returnValue.setPageSize(postPage.getSize());
        returnValue.setTotalElements(postPage.getTotalElements());
        returnValue.setTotalPages(postPage.getTotalPages());
        returnValue.setLast(postPage.isLast());
        return returnValue;
    }
}
