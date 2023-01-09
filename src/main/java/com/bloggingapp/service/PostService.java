package com.bloggingapp.service;

import com.bloggingapp.entities.Post;
import com.bloggingapp.payloads.PostDto;
import com.bloggingapp.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);
    PostDto updatePost(PostDto postDto , Integer postId);
    String deletePost(Integer postId);
    PostResponse getAllPosts(Integer pageNumber , Integer pageSize,String sortBy, String sortDir);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);

}
