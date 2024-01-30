package com.pankiv.blog.controller;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public Post addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @GetMapping()
    public List<Post> getPostsList(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String sort) {
        return postService.getPostsList(title, sort);
    }

    @GetMapping("star")
    public List<Post> getPostsListWithStar() {
        return postService.getPostsListWithStar();
    }

    @PutMapping("{id}")
    public Post changePost(@PathVariable Long id,
                           @RequestBody Post post) {
        return postService.changePost(id, post);
    }

    @PutMapping("{id}/star")
    public Post markPostStarTrue(@PathVariable Long id) {
         return postService.markPostStarTrue(id);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @DeleteMapping("{id}/star")
    public Post markPostStarFalse(@PathVariable Long id) {
        return postService.markPostStarFalse(id);
    }

}
