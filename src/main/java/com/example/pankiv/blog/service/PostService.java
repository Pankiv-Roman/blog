package com.example.pankiv.blog.service;

import com.example.pankiv.blog.entity.Post;

import java.util.List;

public interface PostService {
    public Post savePost(Post post);

    public List<Post> fetchPostsList(String title, String sort);

    public Post changePost(Long id, Post post);

    public Post deletePost(long id);

}
