package com.pankiv.blog.repository;

import com.pankiv.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByPostIdAndId(long postId, long id);
    void deleteByPostId(long id);
}
