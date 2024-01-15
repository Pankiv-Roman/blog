package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;

import java.util.List;

public interface PostService {
    Post savePost(Post post);

    List<Post> fetchPostsList(String title, String sort);

    Post changePost(long id, Post post);

    void deletePost(long id);

    Post markPostStarTrue(long id);

    Post markPostStarFalse(long id);

    List<Post> fetchPostsListWithStar();
}
