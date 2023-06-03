package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;

import java.util.List;

public interface PostService {
    Post savePost(Post post);

    List<Post> fetchPostsList(String title, String sort);

    Post changePost(Long id, Post post);

    Post deletePost(long id);
}
