package com.pankiv.blog.controller;

import com.pankiv.blog.entity.Tag;
import com.pankiv.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class TagController {

    private final TagService tagService;

    @PostMapping("/{postId}/tags")
    public ResponseEntity<String> addTagToPost(@PathVariable Long postId, @RequestBody Tag tag) {
        tagService.addTagToPost(postId, tag);
        return new ResponseEntity<>("Tag added to post successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}/tags")
    public ResponseEntity<String> deleteTag(@PathVariable Long tagId) {
        tagService.deleteTagById(tagId);
        return new ResponseEntity<>("Tag delete successfully", HttpStatus.OK);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}