package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testDeletePost() {
        long postId = 1L;
        Post post = new Post(postId, "Test Title", "Test Content");

        when(postRepository.getById(postId)).thenReturn(post);

        Post deletedPost = postService.deletePost(postId);

        verify(postRepository, times(1)).getById(postId);
        verify(postRepository, times(1)).delete(post);

        assertEquals(post, deletedPost);
    }

    @Test
    public void testMarkPostStar() {
        Post post = new Post();
        post.setId(1L);
        post.setStar(false);

        when(postRepository.getById(1L)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updatedPost = postService.markPostStar(1L, true);

        verify(postRepository, times(1)).getById(1L);
        verify(postRepository, times(1)).save(post);

        Assertions.assertTrue(updatedPost.isStar());
    }

    @Test
    public void testDeleteMarkWithPostStar() {
        Post post = new Post();
        post.setId(1L);
        post.setStar(true);

        when(postRepository.getById(1L)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updatedPost = postService.deleteMarkWithPost(1L);

        verify(postRepository, times(1)).getById(1L);
        verify(postRepository, times(1)).save(post);

        Assertions.assertFalse(updatedPost.isStar());
    }
}