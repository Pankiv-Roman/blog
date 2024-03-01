package com.pankiv.blog.service;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.CommentRepository;
import com.pankiv.blog.repository.PostRepository;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Test mark post star true")
    void testMarkPostStarTrue() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setStar(false);
        postRepository.save(post);

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
        commentRepository.save(comment);

        postService.deletePost(post.getId());

        assertNotNull(comment);
        assertNotNull(post);

        Post retrievedPost = postRepository.findById(post.getId()).orElse(null);
        assertNull(retrievedPost);
    }

    @Test
    @DisplayName("Test delete Post with non-existing post should throw EntityNotFoundException")
    void testExceptionDeletePost() {
        assertThrows(EntityNotFoundException.class, () -> {
            postService.deletePost(0);
        });
    }

    @Test
    @DisplayName("Test change post")
    void testChangePost() {
        Post post = new Post();
        post.setTitle("Test title");
        post.setContent("Test content");
        postRepository.save(post);


        postService.changePost(post.getId(), post);
        post.setTitle("Change title");
        post.setContent("Change content");

        assertEquals("Change title", post.getTitle());
        assertEquals("Change content", post.getContent());
    }

    @Test
    @DisplayName("Test save post")
    void testSavePost() {
        Post postToSave = new Post();
        postRepository.save(postToSave);

        Post savePost = postService.addPost(postToSave);

        assertNotNull(savePost);
        assertEquals(postToSave.getId(), savePost.getId());
    }
}