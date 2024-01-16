package com.pankiv.blog.controller;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import com.pankiv.blog.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    List<Comment> comments;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    private Comment comment1;
    private Comment comment2;

    @Test
    void testFetchComments() throws Exception {
        comment1 = createComment(1L, "testComment1", "2023-12-17T20:43:40.455");
        comment2 = createComment(2L, "testComment2", "2022-10-10T11:40:40.000");

        when(commentService.fetchComments(1L)).thenReturn(comment1, comment2);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/posts/1/comments")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("testComment1"))
                .andExpect(jsonPath("$.creationDate").value("2023-12-17T20:43:40.455+00:00"));
    }

    @Test
    void testGetCommentWithPost() throws Exception {
        comment1 = createComment(1L, "Test comment", "2023-12-17T20:43:40.455");
        Post post = createPost(1L, "Test content", "Test title", true, comments);
        when(commentService.getCommentWithPost(1L, 1L)).thenReturn(comment1);

        mockMvc.perform(get("/api/v1/posts/1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Test comment"));
        verify(commentService, times(1)).getCommentWithPost(1L, 1L);
    }

    @Test
    void testGetCommentsWithPost() throws Exception {
        Post post = createPost(1L, "Test content", "Test title", true, comments);

        comment1 = createComment(1L, "Test text", "2023-12-17T20:43:40.455");
        comment2 = createComment(2L, "Test text2", "2020-11-17T20:40:10.155");


        post.setComments(Arrays.asList(comment1, comment2));

        when(commentService.getCommentsWithPost(1L)).thenReturn(post);

        mockMvc.perform(get("/api/v1/posts/1/full"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.content").value("Test content"))
                .andExpect(jsonPath("$.star").value(true))
                .andExpect(jsonPath("$.comments[0].id").value(1))
                .andExpect(jsonPath("$.comments[0].text").value("Test text"))
                .andExpect(jsonPath("$.comments[0].creationDate").value("2023-12-17T20:43:40.455+00:00"))
                .andExpect(jsonPath("$.comments[1].id").value(2))
                .andExpect(jsonPath("$.comments[1].text").value("Test text2"))
                .andExpect(jsonPath("$.comments[1].creationDate").value("2020-11-17T20:40:10.155+00:00"));

        verify(commentService, times(1)).getCommentsWithPost(1L);
    }

    private Comment createComment(Long id, String text, String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(dateString);

        return Comment.commentBuilder()
                .id(id)
                .text(text)
                .creationDate(LocalDateTime.now())
                .build();
    }

    private Post createPost(Long id, String content, String title, boolean star, List<Comment> comments) {
        return Post.postBuilder()
                .id(id)
                .content(content)
                .title(title)
                .star(star)
                .comments(comments)
                .build();
    }
}
