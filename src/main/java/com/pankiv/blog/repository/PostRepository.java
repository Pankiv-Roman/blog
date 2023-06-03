package com.pankiv.blog.repository;

import com.pankiv.blog.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitle(String title, Sort sort);

    Post findAllByTitleIgnoreCase(String title);

}
