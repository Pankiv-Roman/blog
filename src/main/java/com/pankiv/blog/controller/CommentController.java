package com.pankiv.blog.controller;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.PostRepository;
import com.pankiv.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")

public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;

    @PostMapping("/{postId}/comments")
    public Comment addCommentToPost(@PathVariable Long postId,
                                    @RequestBody Comment comment) {
        Post post = postRepository.getById(postId);
        comment.setPost(post);
        return commentService.addCommentToPost(post, comment);
    }

    @GetMapping("{id}/comments")
    public Comment getComments(@PathVariable Long id) {
        return commentService.getComments(id);
    }

    @GetMapping("/{postId}/comments/{id}")
    public Comment getCommentWithPost(@PathVariable Long postId,
                                      @PathVariable long id) {
        return commentService.getCommentWithPost(postId, id);
    }

    @GetMapping("{postId}/full")
    public Post getCommentsWithPost(@PathVariable Long postId) {
        return commentService.getCommentsWithPost(postId);
    }
}
