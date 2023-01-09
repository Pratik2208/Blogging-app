package com.bloggingapp.service.impl;

import com.bloggingapp.entities.Comment;
import com.bloggingapp.entities.Post;
import com.bloggingapp.exceptions.ResourceNotFoundException;
import com.bloggingapp.payloads.CommentDto;
import com.bloggingapp.repositories.CommentRepository;
import com.bloggingapp.repositories.PostRepository;
import com.bloggingapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        Comment comment = this.mapper.map(commentDto , Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.mapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","comment id",commentId));
        this.commentRepository.delete(comment);
    }
}
