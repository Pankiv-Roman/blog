package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.entity.Tag;
import com.pankiv.blog.repository.PostRepository;
import com.pankiv.blog.repository.PostTagRepository;
import com.pankiv.blog.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTagService implements TagService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    @Transactional
    @Override
    public Post addTagToPost(Long postId, Tag tag) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        post.addTag(tagRepository.save(tag));
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Tag deleteTagById(Long tagId) {
        if (tagRepository.existsById(tagId)) {
            postTagRepository.deletePostTagById(tagId);
            tagRepository.deleteTagById(tagId);
        } else {
            throw new EntityNotFoundException("Tag not found with id: " + tagId);
        }
        return null;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
