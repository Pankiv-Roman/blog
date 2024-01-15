package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.CommentRepository;
import com.pankiv.blog.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> fetchPostsList(String title, String sortString) {
        Sort sort = sortString == null ? Sort.unsorted() : Sort.by(sortString);
        if (title != null) {
            return postRepository.findAllByTitle(title, sort);
        }
        return postRepository.findAll(sort);
    }

    @Override
    public Post changePost(long id, Post post) {
        Optional<Post> existingPost = postRepository.findById(id);
        postRepository.getReferenceById(id);
        Post postToUpdate = existingPost.get();
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContent(post.getContent());
        return postRepository.save(postToUpdate);
    }

    @Transactional
    @Override
    public void deletePost(long id) {
        if (postRepository.existsById(id)) {
            commentRepository.deleteById(id);
            postRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Post with id:" + id + " not found!");
        }
    }

    @Transactional
    @Override
    public Post markPostStarTrue(long id) {
        postRepository.markPostStar(id, true);
        return postRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Post markPostStarFalse(long id) {
        postRepository.markPostStar(id, false);
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> fetchPostsListWithStar() {
        return postRepository.findAllByStar(true);
    }
}