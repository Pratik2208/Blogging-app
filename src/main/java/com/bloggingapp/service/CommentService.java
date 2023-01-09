package com.bloggingapp.service;

import com.bloggingapp.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto , Integer postId);
    void deleteComment(Integer commentId);
}
