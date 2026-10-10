package com.example.mapper;

import com.example.dto.CategoryRequestDto;
import com.example.dto.CategoryResponseDto;
import com.example.entity.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDto dto) {

        Category category = new Category();

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return category;
    }

    public static CategoryResponseDto toResponseDto(Category category) {

        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}