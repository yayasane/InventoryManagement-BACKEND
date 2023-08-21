package com.yayaveli.inventorymanagement.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayaveli.inventorymanagement.models.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private Integer id;
    private String categoryCode;
    private String designation;
    @JsonIgnore
    private List<ItemDto> items;

    public static CategoryDto fromEntity(Category category) {
        if (category == null) {
            return null;
            // TODO throw an exception

        }
        return CategoryDto.builder()
                .id(category.getId())
                .categoryCode(category.getCategoryCode())
                .designation(category.getDesignation())
                .build();
    };

    public static Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
            // TODO throw an exception

        }
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCategoryCode(categoryDto.getCategoryCode());
        category.setDesignation(categoryDto.getDesignation());

        return category;
    };
}
