package com.bloggingapp.controllers;

import com.bloggingapp.payloads.CommentDto;
import com.bloggingapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService service;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto
    , @PathVariable("postId")Integer postId){
      CommentDto dto = this.service.createComment(commentDto,postId);
      return new ResponseEntity<CommentDto>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}/comments")
    public String deleteComment(@PathVariable("commentId")Integer commentId){
        this.service.deleteComment(commentId);
        return "Deleted Successfully.......";
    }
}
