package com.pankiv.blog.repository;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitle(String title, Sort sort);

    List<Post> findAllByStar(boolean star);

    @Modifying
    @Query("UPDATE Post p SET p.star = :star where p.id = :id")
    void markPostStar(long id, boolean star);

    Post findPostWithCommentsById(long id);
}
