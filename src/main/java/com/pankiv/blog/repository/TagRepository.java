package com.pankiv.blog.repository;

import com.pankiv.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteTagById(Long tadId);

    List<Tag> findByNameIn(Set<String> tagNames);
}
