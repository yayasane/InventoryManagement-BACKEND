package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.CategoryApi;
import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryApi {
    private CategoryService categoryService;
    // Field Injection
    /*
     * @Autowired
     * private CategoryService categoryService;
     */

    // Setter Injection
    /*
     * @Autowired
     * public void setCategoryService(CategoryService categoryService) {
     * this.categoryService = categoryService;
     * }
     */

    // Constructor Injection
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public CategoryDto save(CategoryDto categoryDto) {
        return this.categoryService.save(categoryDto);
    }

    @Override
    public CategoryDto findById(Integer id) {
        return this.categoryService.findById(id);
    }

    @Override
    public CategoryDto findByCategoryCode(String categoryCode) {
        return this.categoryService.findByCategoryCode(categoryCode);
    }

    @Override
    public List<CategoryDto> findAll() {
        return this.categoryService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.categoryService.delete(id);

    }

}
