package com.pankiv.blog.service;

import com.pankiv.blog.entity.Comment;
import com.pankiv.blog.entity.Post;

public interface CommentService {
    Comment getComments(long id);

    Comment getCommentWithPost(long postId, long id);

    Comment addCommentToPost(Post post, Comment comment);

    Post getCommentsWithPost(Long postId);
}
