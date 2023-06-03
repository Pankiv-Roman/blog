package com.pankiv.blog.controller;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping()
    public Post savePost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @GetMapping()
    public List<Post> fetchPostsList(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String sort) {
        return postService.fetchPostsList(title, sort);
    }

    @PutMapping("{id}")
    public Post changePost(@PathVariable long id,
                           @RequestBody Post post) {
        return postService.changePost(id, post);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable long id) {
        postService.deletePost(id);
    }

}
