package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.CategoryDto;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);

    CategoryDto findById(Integer id);

    CategoryDto findByCategoryCode(String categoryCode);

    List<CategoryDto> findAll();

    void deleteInteger(Integer id);
}
