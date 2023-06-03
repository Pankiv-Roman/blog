package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.PostRepository;
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
}