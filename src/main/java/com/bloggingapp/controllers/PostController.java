package com.bloggingapp.controllers;

import com.bloggingapp.entities.Post;
import com.bloggingapp.payloads.PostDto;
import com.bloggingapp.payloads.PostResponse;
import com.bloggingapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService service;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto , @PathVariable("userId") Integer
                                              userId , @PathVariable("categoryId") Integer categoryId){
        PostDto createdPost = this.service.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId){
        List<PostDto> postDtos = this.service.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable("userId") Integer userId){
       List<PostDto> postDtos = this.service.getPostsByUser(userId);
       return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts
            (@RequestParam(value = "pageNumber",required = false)Integer pageNumber,
             @RequestParam(value = "pageSize",required = false)Integer pageSize,
             @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        PostResponse response = this.service.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable("userId") Integer userId){
       PostDto post =this.service.getPostById(userId);
       return new ResponseEntity<PostDto>(post , HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/post")
    public String deletePost(@PathVariable("postId") Integer postId){
        return this.service.deletePost(postId);
    }

    @PutMapping("/{userId}/post")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable("userId") Integer userId){
        PostDto dto = this.service.updatePost(postDto , userId);
        return new ResponseEntity<PostDto>(dto , HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> search(@PathVariable("keyword")String keyword){
       List<PostDto> dtos = this.service.searchPosts(keyword);
       return new ResponseEntity<List<PostDto>>(dtos,HttpStatus.OK);
    }
}
