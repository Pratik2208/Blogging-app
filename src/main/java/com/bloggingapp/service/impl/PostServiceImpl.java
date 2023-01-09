package com.bloggingapp.service.impl;

import com.bloggingapp.entities.Category;
import com.bloggingapp.entities.Post;
import com.bloggingapp.entities.User;
import com.bloggingapp.exceptions.ResourceNotFoundException;
import com.bloggingapp.payloads.PostDto;
import com.bloggingapp.payloads.PostResponse;
import com.bloggingapp.repositories.CategoryRepository;
import com.bloggingapp.repositories.PostRepository;
import com.bloggingapp.repositories.UserRepository;
import com.bloggingapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.*;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId , Integer categoryId) {
        // First finding the user
        User user = this.userRepository.findById(userId)
                       .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));
        Post post = this.mapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post addedPost = this.postRepository.save(post);
        return this.mapper.map(addedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepository.save(post);
        return this.mapper.map(updatedPost , PostDto.class);
    }

    @Override
    public String deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        this.postRepository.delete(post);
        return "Post Deleted Successfully.....";
    }

    // Below method also implements sorting and paging
    @Override
    public PostResponse getAllPosts(Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

         Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
         Page<Post> pagePost = this.postRepository.findAll(pageable);
         List<Post> allPosts= pagePost.getContent();
         List<PostDto> postDtos =
                 allPosts.stream().map((post) -> this.mapper.map(post,PostDto.class)).collect(Collectors.toList());
         PostResponse response = new PostResponse();
         response.setContent(postDtos);
         response.setPageNumber(pagePost.getNumber());
         response.setPageSize(pagePost.getSize());
         response.setTotalElements(pagePost.getTotalElements());
         response.setTotalPages(pagePost.getTotalPages());
         response.setLastPage(pagePost.isLast());
         return response;
    }

    @Override
    public PostDto getPostById(Integer postId) {
       Post post = this.postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        return this.mapper.map(post, PostDto.class);
    }

    // Below two methods are very important
    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category obtained = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));
        List<Post> posts = this.postRepository.findByCategory(obtained);

        // Converting each obtained post into postDto class
        List<PostDto> postDtos =
                posts.stream().map(post -> this.mapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        List<Post> posts = this.postRepository.findByUser(user);

        List<PostDto> postDtos =
                posts.stream().map(post -> this.mapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepository.findByTitleContaining("%"+keyword+"%");
        List<PostDto> dtos =posts.stream().map((post) -> this.mapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return dtos;
    }
}
