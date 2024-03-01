package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.entity.Tag;
import com.pankiv.blog.repository.PostRepository;
import com.pankiv.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    @Override
    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getPostsList(String title, String sortString) {
        Sort sort = sortString == null ? Sort.unsorted() : Sort.by(sortString);
        if (title != null) {
            return postRepository.findAllByTitle(title, sort);
        }
        return postRepository.findAll(sort);
    }

    @Override
    public Post changePost(long id, Post post) {
        Optional<Post> existingPost = postRepository.findById(id);
        Post postToUpdate = existingPost.get();
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContent(post.getContent());
        return postRepository.save(postToUpdate);
    }

    @Transactional
    @Override
    public void deletePost(long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Post with id:" + id + " not found!");
        }
    }

    @Transactional
    @Override
    public Post markPostStarTrue(long id) {
        postRepository.markPostStar(id, true);
        return postRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Post with id:" + id + " not found!"));
    }

    @Transactional
    @Override
    public Post markPostStarFalse(long id) {
        postRepository.markPostStar(id, false);
        return postRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Post with id:" + id + " not found!"));
    }

    @Override
    public List<Post> getPostsListWithStar() {
        return postRepository.findAllByStar(true);
    }

    @Override
    public List<Post> getPostsByTagNames(Set<String> tagNames) {
        Set<Tag> tags = new HashSet<>(tagRepository.findByNameIn(tagNames));
        return postRepository.findDistinctByTagsIn(tags);
    }
}