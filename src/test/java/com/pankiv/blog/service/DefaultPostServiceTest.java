package com.pankiv.blog.service;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import com.pankiv.blog.entity.Tag;
import com.pankiv.blog.repository.CommentRepository;
import com.pankiv.blog.repository.PostRepository;

import javax.persistence.EntityNotFoundException;

import com.pankiv.blog.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DefaultPostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;

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
    @Test
    @DisplayName("Test get post by tags")
    void testGetPostsByTagNames() {
        Tag tag1 = new Tag();
        tag1.setName("Tag1");
        Tag tag2 = new Tag();
        tag2.setName("Tag2");
        tagRepository.saveAll(Arrays.asList(tag1, tag2));

        Post post1 = new Post();
        post1.setTitle("Title1");
        post1.setContent("Content1");
        post1.addTag(tag1);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setTitle("Title2");
        post2.setContent("Content2");
        post2.addTag(tag1);
        post2.addTag(tag2);
        postRepository.save(post2);

        Set<String> tagNames = new HashSet<>(Arrays.asList("Tag1", "Tag2"));
        List<Post> posts = postService.getPostsByTagNames(tagNames);

        assertNotNull(posts);
        assertEquals(2, posts.size());

        List<String> titles = posts.stream().map(Post::getTitle).toList();
        assertTrue(titles.contains("Title1"));
        assertTrue(titles.contains("Title2"));
    }

    @AfterEach
    @Transactional
    void tearDown() {
        postRepository.deleteAll();
        tagRepository.deleteAll();
    }
}