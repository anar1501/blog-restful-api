package com.company.blogapi.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@SuppressWarnings(value = "ALL")
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
