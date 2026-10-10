package com.example.serviceimpl;

import com.example.dto.CategoryRequestDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import com.example.exception.DuplicateResourceException;
import com.example.exception.ResourceNotFoundException;
import com.example.dto.CategoryResponseDto;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @CacheEvict(value = "allCategories", allEntries = true)
    public CategoryResponseDto createCategory(
            CategoryRequestDto request) {

        log.info(
                "Creating category with name: {}",
                request.getName()
        );

        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Category already exists"
            );
        }

        Category category =
                CategoryMapper.toEntity(request);

        Category savedCategory =
                categoryRepository.save(category);

        log.info(
                "Category created successfully with ID: {}",
                savedCategory.getId()
        );

        return CategoryMapper.toResponseDto(savedCategory);
    }
    @Cacheable(value = "categories", key = "#id")
    @Override
    public CategoryResponseDto getCategoryById(Long id) {

        log.info("Fetching category with ID: {}", id);

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"
                                ));

        return CategoryMapper.toResponseDto(category);
    }
    @Cacheable(value = "allCategories")
    @Override
    public List<CategoryResponseDto> getAllCategories() {

        log.info("Fetching all categories");

        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }
    @Override
    @Caching(evict = {
    	    @CacheEvict(value = "categories", key = "#id"),
    	    @CacheEvict(value = "allCategories", allEntries = true)
    	})
    public CategoryResponseDto updateCategory(
            Long id,
            CategoryRequestDto request) {

        log.info("Updating category with ID: {}", id);

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"
                                ));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updatedCategory =
                categoryRepository.save(category);

        log.info(
                "Category updated successfully with ID: {}",
                id
        );

        return CategoryMapper.toResponseDto(updatedCategory);
    }
    @Override
    @Caching(evict = {
    	    @CacheEvict(value = "categories", key = "#id"),
    	    @CacheEvict(value = "allCategories", allEntries = true)
    	})
    public void deleteCategory(Long id) {

        log.info("Deleting category with ID: {}", id);

        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Category not found"
            );
        }

        categoryRepository.deleteById(id);

        log.info(
                "Category deleted successfully with ID: {}",
                id
        );
    }
}