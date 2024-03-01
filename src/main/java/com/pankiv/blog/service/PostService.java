package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import java.util.List;
import java.util.Set;

public interface PostService {

    Post addPost(Post post);

    List<Post> getPostsList(String title, String sort);

    Post changePost(long id, Post post);

    void deletePost(long id);

    Post markPostStarTrue(long id);

    Post markPostStarFalse(long id);

    List<Post> getPostsListWithStar();

    List<Post> getPostsByTagNames(Set<String> tagNames);
}
