package com.bloggingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(nullable = false)
    private String Title;
    private String content;
    private String imageName;
    private Date addedDate;

   @JsonIgnore
    @ManyToOne
    private User user;

   @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
