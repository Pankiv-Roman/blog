package com.pankiv.blog.controller;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
                Post.builder()
                        .id(1L)
                        .content("testContent")
                        .title("testTitle2")
                        .star(false)
                        .build();
        post2 =
                Post.builder()
                        .id(2L)
                        .content("testContent2")
                        .title("testTitle1")
                        .star(true)
                        .build();

        posts = Arrays.asList(post1, post2);
    }

    @Test
    void testSavePost() throws Exception {
        Post inputPost =
                Post.builder()
                        .content("testContent")
                        .title("testTitle")
                        .build();
        when(postService.savePost(inputPost))
                .thenReturn(post1);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"content\": \"testContent\",\n" +
                                "    \"title\": \"testTitle\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void testFetchPostsList() throws Exception {
        when(postService.fetchPostsList(null, null)).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("testTitle2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("testContent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("testTitle1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("testContent2"));

    }

    @Test
    void testFetchPostsListToTitle() throws Exception {
        when(postService.fetchPostsList("testTitle1", null)).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .param("title", "testTitle1")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("testTitle1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("testContent2"));
    }

    @Test
    void testFetchPostsListWithSort() throws Exception {
        when(postService.fetchPostsList(null, "title")).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .param("sort", "title")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("testTitle1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("testContent2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("testTitle2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("testContent"));


    }


    @Test
    void testChangePost() throws Exception {
        this.mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"content\": \"testContent111\",\n" +
                                "    \"title\": \"testTitle111\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void fetchPostsListWithStar() throws Exception {
        when(postService.fetchPostsListWithStar()).thenReturn(posts);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/star")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("testTitle1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content").value("testContent2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].star").value(true));

    }
}