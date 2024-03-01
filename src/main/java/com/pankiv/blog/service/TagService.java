package com.pankiv.blog.service;

import com.pankiv.blog.entity.Tag;

import java.util.List;

public interface TagService {
    void addTagToPost(Long postId, Tag tag);

    void deleteTagById(Long tagId);

    List<Tag> getAllTags();
}
