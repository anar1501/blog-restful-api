package com.company.blogapi.repository;

import com.company.blogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PagingAndSortingRepository<Post,Long> {
}
