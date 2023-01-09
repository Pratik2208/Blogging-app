package com.bloggingapp.repositories;

import com.bloggingapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {

}
