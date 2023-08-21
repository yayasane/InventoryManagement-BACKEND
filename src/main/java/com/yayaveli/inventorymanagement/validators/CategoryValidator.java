package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.CategoryDto;

import org.springframework.util.StringUtils;

public class CategoryValidator {

    public static List<String> validate(CategoryDto categoryDto) {
        List<String> errors = new ArrayList<>();

        if (categoryDto == null || !StringUtils.hasLength(categoryDto.getCategoryCode())) {
            errors.add("Veuillez renseigner le code de la catégorie");
        }

        return errors;
    }
}
