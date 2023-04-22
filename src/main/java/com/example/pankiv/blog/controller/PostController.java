package com.example.pankiv.blog.controller;

import com.example.pankiv.blog.entity.Post;
import com.example.pankiv.blog.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("api/v1/posts")
    public Post savePost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @GetMapping("api/v1/posts")
    public List<Post> fetchPostsList(@RequestParam (required = false) String title, @RequestParam (required = false) String sort) {
        return postService.fetchPostsList(title, sort);
    }

    @PutMapping("api/v1/posts/{id}")
    public Post changePost(@PathVariable(value = "id") long id,
                           @RequestBody Post post) {
        return postService.changePost(id, post);
    }
    @DeleteMapping("api/v1/posts/{id}")
    public void deletePost(@PathVariable(value = "id") long id) {
        postService.deletePost(id);
    }

}
