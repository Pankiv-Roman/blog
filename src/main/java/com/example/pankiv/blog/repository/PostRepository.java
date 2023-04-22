package com.example.pankiv.blog.repository;

import com.example.pankiv.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>  {
    List<Post> findAllByTitle(String title);
}
