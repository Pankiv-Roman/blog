package com.pankiv.blog.service;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;
import com.pankiv.blog.repository.CommentRepository;
import com.pankiv.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;


    private final PostRepository postRepository;

    @Override
    public Comment getComments(long id) {
        return commentRepository.getReferenceById(id);
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
        return commentRepository.save(comment);
    }

    @Override
    public Post getCommentsWithPost(Long postId) {
        return postRepository.findPostWithCommentsById(postId);
    }
}
