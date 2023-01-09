package com.bloggingapp.repositories;

import com.bloggingapp.entities.Category;
import com.bloggingapp.entities.Post;
import com.bloggingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    public List<Post> findByUser(User user);
    public List<Post> findByCategory(Category category);

    @Query("select p from Post p where p.Title like :key")
    public List<Post> findByTitleContaining(@Param("key") String title);
}
