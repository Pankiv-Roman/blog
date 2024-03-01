package com.pankiv.blog.repository;

import com.pankiv.blog.entity.PostTag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    @Modifying
    @Transactional

    void deletePostTagById(@Param("tagId") Long tagId);
}
