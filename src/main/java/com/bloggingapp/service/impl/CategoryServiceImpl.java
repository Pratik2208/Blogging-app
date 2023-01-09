package com.bloggingapp.service.impl;

import com.bloggingapp.entities.Category;
import com.bloggingapp.exceptions.ResourceNotFoundException;
import com.bloggingapp.payloads.CategoryDto;
import com.bloggingapp.repositories.CategoryRepository;
import com.bloggingapp.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.mapper.map(categoryDto , Category.class);
        Category addedCategory = this.repository.save(category);
        return this.mapper.map(addedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.repository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.repository.save(category);
        return this.mapper.map(updatedCategory , CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.repository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        this.repository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category obtainedCategory = this.repository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
        return this.mapper.map(obtainedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.repository.findAll();
        List<CategoryDto> dtos = (List<CategoryDto>) categories.stream().map((cat)-> this.mapper.map(cat,CategoryDto.class))
                .collect(Collectors.toList());
        return dtos;
    }
}
