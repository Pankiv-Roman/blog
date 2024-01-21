package com.pankiv.blog.service;

import com.pankiv.blog.repository.CommentRepository;
import com.pankiv.blog.repository.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class TestConfig {

    @Bean
    public PostService postService(PostRepository postRepository, CommentRepository commentRepository) {
        return new PostServiceImpl(postRepository, commentRepository);
    }

}

