package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.entity.Tag;

import java.util.List;

public interface TagService {
    Post addTagToPost(Long postId, Tag tag);

    Tag deleteTagById(Long tagId);

    List<Tag> getAllTags();
}
