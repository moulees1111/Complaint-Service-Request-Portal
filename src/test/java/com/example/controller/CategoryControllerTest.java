package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.dto.CategoryResponseDto;
import com.example.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void getAllCategoriesShouldReturnOk() {

        when(categoryService.getAllCategories())
                .thenReturn(List.of());

        ResponseEntity<?> response =
                categoryController.getAllCategories();

        assertEquals(200, response.getStatusCode().value());

        verify(categoryService).getAllCategories();
    }
}