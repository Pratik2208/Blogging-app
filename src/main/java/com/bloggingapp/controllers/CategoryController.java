package com.bloggingapp.controllers;

import com.bloggingapp.payloads.ApiResponse;
import com.bloggingapp.payloads.CategoryDto;
import com.bloggingapp.service.CategoryService;
import jakarta.validation.Valid;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto dto = this.service.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory (@Valid @RequestBody CategoryDto categoryDto
    , @PathVariable Integer categoryId){
        CategoryDto dto = this.service.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<CategoryDto>(dto,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory (@PathVariable Integer categoryId){
        CategoryDto dto = this.service.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.service.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category Deleted Successfully !!!",
                true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categories = this.service.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
