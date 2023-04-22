package com.example.pankiv.blog.service;

import com.example.pankiv.blog.entity.Post;
import com.example.pankiv.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> fetchPostsList(String title, String sort) {
        Sort sorted = sort == null ? Sort.unsorted() : Sort.by(sort);
        if (title != null) {
            return postRepository.findAllByTitle(title);
        }
        else {
            return postRepository.findAll(sorted);
        }
    }

    @Override
    public Post changePost(Long id, Post post) {
        post.setId(id);
        return postRepository.save(post);
    }

    @Override
    public Post deletePost(long id) {
        Post post = postRepository.getById(id);
        postRepository.delete(post);
        return post;
    }

}
