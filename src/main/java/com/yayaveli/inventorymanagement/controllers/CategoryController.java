package com.yayaveli.inventorymanagement.controllers;

import com.yayaveli.inventorymanagement.controllers.api.CategoryApi;
import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    public ResponseEntity<CategoryDto> save(CategoryDto categoryDto) {
        return ResponseEntity.ok().body(this.categoryService.save(categoryDto));
    }

    @Override
    public ResponseEntity<CategoryDto> findById(Integer id) {
        return ResponseEntity.ok().body(this.categoryService.findById(id));
    }

    @Override
    public ResponseEntity<CategoryDto> findByCategoryCode(String categoryCode) {
        return ResponseEntity.ok().body(this.categoryService.findByCategoryCode(categoryCode));
    }

    @Override
    public ResponseEntity<List<CategoryDto>> findAll() {
        return ResponseEntity.ok().body(this.categoryService.findAll());
    }

    @Override
    public ResponseEntity delete(Integer id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
