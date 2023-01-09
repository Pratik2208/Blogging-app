package com.bloggingapp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role",referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

}
