package com.pankiv.blog.service;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.CommentRepository;
import com.pankiv.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment fetchComments(long id) {
        return commentRepository.getById(id);
    }

    @Override
    public Comment getCommentWithPost(long postId, long id) {
        return commentRepository.findByPostIdAndId(postId, id);
    }

    @Override
    public Comment addCommentToPost(Post post, Comment comment) {
        List<Comment> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);
        commentRepository.save(comment);
        return commentRepository.save(post);
    }

    @Override
    public Comment getCommentsWithPost(Long postId) {
        return postRepository.findById(postId)
                .orElse(null);
    }
}
