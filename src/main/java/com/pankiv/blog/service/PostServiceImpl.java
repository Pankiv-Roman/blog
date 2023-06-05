package com.pankiv.blog.service;

import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.PostRepository;
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
    public List<Post> fetchPostsList(String title, String sortString) {
        Sort sort = sortString == null ? Sort.unsorted() : Sort.by(sortString);
        if (title != null) {
            return postRepository.findAllByTitle(title, sort);
        }
        return postRepository.findAll(sort);
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

    @Override
    public Post markPostStar(long id, boolean star) {
        Post post = postRepository.getById(id);
        post.setStar(true);
        return postRepository.save(post);
    }

    @Override
    public Post deleteMarkWithPost(long id) {
        Post post = postRepository.getById(id);
        post.setStar(false);
        return postRepository.save(post);
    }

    @Override
    public List<Post> fetchPostsListWithStar() {
        return postRepository.findAllByStar(true);
    }
}
