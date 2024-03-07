package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.entity.Tag;
import com.pankiv.blog.repository.PostRepository;
import com.pankiv.blog.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DefaultTagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("Add existing tag to post.")
    void testAddTagToPost() {
        Post post = new Post();
        post.setTitle("testTitle");
        post.setContent("testContent");
        postRepository.save(post);

        Tag tag = new Tag();
        tag.setName("testTag");
        tagRepository.save(tag);

        Post updatePost = tagService.addTagToPost(post.getId(), tag);

        assertNotNull(updatePost);
        assertEquals("testTitle", updatePost.getTitle());
        assertEquals("testContent", updatePost.getContent());
        assertTrue(updatePost.getTags().contains(tag));
    }


    @Test
    @DisplayName("Delete tag by id.")
    void testDeleteTagById() {
        Post post = new Post();
        post.setTitle("testTitle");
        post.setContent("testContent");
        postRepository.save(post);

        Tag tag = new Tag();
        tag.setName("testTag");
        tagRepository.save(tag);

        Tag deletedTag = tagService.deleteTagById(tag.getId());

        assertNull(deletedTag);
    }

    @Test
    @DisplayName("Get list tags.")
    void testGetAllTags() {
        Tag tag1 = new Tag();
        tag1.setName("testTag1");
        tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("testTag2");
        tagRepository.save(tag2);

        List<Tag> tags = tagService.getAllTags();

        assertNotNull(tags);

        assertEquals(2, tags.size());
        assertTrue(tags.contains(tag1));
        assertTrue(tags.contains(tag2));
    }

    @AfterEach
    @Transactional
    void tearDown() {
        postRepository.deleteAll();
        tagRepository.deleteAll();
    }
}