package com.bloggingapp.payloads;

import com.bloggingapp.entities.Category;
import com.bloggingapp.entities.Comment;
import com.bloggingapp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<Comment> comments = new HashSet<>();
}
