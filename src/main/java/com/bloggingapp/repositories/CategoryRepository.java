package com.bloggingapp.repositories;

import com.bloggingapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {
}
