package com.example.service;

import com.example.dto.CategoryRequestDto;
import com.example.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto request);

    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto updateCategory(
            Long id,
            CategoryRequestDto request
    );

    void deleteCategory(Long id);
}