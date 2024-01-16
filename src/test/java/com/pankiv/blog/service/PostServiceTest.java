package com.pankiv.blog.service;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import static org.junit.jupiter.api.Assertions.*;

@Import({TestConfig.class})
@DataJpaTest
public class PostServiceTest {

    private final PostService postService;
    private final PostRepository postRepository;

    @Autowired
    public PostServiceTest(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @Test
    @DisplayName("Test mark post star true")
    void testMarkPostStarTrue() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setStar(false);
        postRepository.save(post);
//        postRepository.save(post);

        Post updatedPost = postService.markPostStarTrue(post.getId());

        assertNotNull(updatedPost);
        assertTrue(updatedPost.isStar());

        Post retrievedPost = postRepository.findById(post.getId()).orElse(null);
        assertNotNull(retrievedPost);
        assertTrue(retrievedPost.isStar());
    }

    @Test
    @DisplayName("Test mark post star false")
    void testMarkPostStarFalse() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setStar(true);
        postRepository.save(post);

        Post updatedPost = postService.markPostStarFalse(post.getId());

        assertNotNull(updatedPost);
        assertFalse(updatedPost.isStar());

        Post retrievedPost = postRepository.findById(post.getId()).orElse(null);
        assertNotNull(retrievedPost);
        assertFalse(retrievedPost.isStar());
    }

    @Test
    @DisplayName("Test delete post")
    void testDeletePost() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setStar(true);
        postRepository.save(post);

        postService.deletePost(post.getId());

        assertNotNull(post);

        Post retrievedPost = postRepository.findById(post.getId()).orElse(null);
        assertNull(retrievedPost);
    }

    @Test
    @DisplayName("Test delete post with comment")
    void testDeletePostWithComment() {
        Post post = new Post();
        post.setTitle("Test title");
        post.setContent("Test content");
        post.setStar(true);
        postRepository.save(post);
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setId(comment.getId());
        comment.setText("Test text");

        postService.deletePost(post.getId());

        assertNotNull(post);

        Post retrievedPost = postRepository.findById(post.getId()).orElse(null);
        assertNull(retrievedPost);
    }

    @Test
    @DisplayName("Test deletePost with non-existing post should throw EntityNotFoundException")
    void testExceptionDeletePost() {
        assertThrows(EntityNotFoundException.class, () -> {
            postService.deletePost(0);
        });

    }

}

