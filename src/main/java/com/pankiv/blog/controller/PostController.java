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

    @GetMapping("star")
    public List<Post> fetchPostsListWithStar() {
        return postService.fetchPostsListWithStar();
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
