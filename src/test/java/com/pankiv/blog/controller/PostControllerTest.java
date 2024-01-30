package com.pankiv.blog.controller;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostControllerTest {

    List<Post> posts;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;
    private Post post1;
    private Post post2;

    @BeforeEach
    void setUp() {
        post1 =
                Post.postBuilder()
                        .id(1L)
                        .content("testContent")
                        .title("testTitle2")
                        .star(false)
                        .comments(null)
                        .build();
        post2 =
                Post.postBuilder()
                        .id(2L)
                        .content("testContent2")
                        .title("testTitle1")
                        .star(true)
                        .comments(null)
                        .build();

        posts = Arrays.asList(post1, post2);
    }


    @Test
    @DisplayName("Test add new post")
    void testAddPost() throws Exception {
        when(postService.addPost(post1)).thenReturn(post1);

        String resultActions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\": \"Add Content\", \"title\": \"Add Title\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @DisplayName("Test get list posts")
    void testGetPostsList() throws Exception {
        when(postService.getPostsList(null, null)).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("testTitle2"))
                .andExpect(jsonPath("$[0].content").value("testContent"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("testTitle1"))
                .andExpect(jsonPath("$[1].content").value("testContent2"));
    }

    @Test
    @DisplayName("Test get list posts with input in the title")
    void testGetPostsListToTitle() throws Exception {
        when(postService.getPostsList("testTitle1", null)).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .param("title", "testTitle1")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("testTitle1"))
                .andExpect(jsonPath("$[1].content").value("testContent2"));
    }

    @Test
    @DisplayName("Test get list post with sort by title")
    void testGetPostsListWithSort() throws Exception {
        when(postService.getPostsList(null, "title")).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .param("sort", "title")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("testTitle1"))
                .andExpect(jsonPath("$[1].content").value("testContent2"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("testTitle2"))
                .andExpect(jsonPath("$[0].content").value("testContent"));


    }

    @Test
    @DisplayName("Test get list posts with star true")
    void testGetPostsListWithStar() throws Exception {
        when(postService.getPostsListWithStar()).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/star")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("testTitle1"))
                .andExpect(jsonPath("$[1].content").value("testContent2"))
                .andExpect(jsonPath("$[1].star").value(true));

    }
}