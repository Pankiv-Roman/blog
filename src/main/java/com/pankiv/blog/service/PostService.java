package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Access;
import java.util.List;

public interface PostService {

    Post addPost(Post post);

    List<Post> getPostsList(String title, String sort);

    Post changePost(long id, Post post);

    void deletePost(long id);

    Post markPostStarTrue(long id);

    Post markPostStarFalse(long id);

    List<Post> getPostsListWithStar();
}
