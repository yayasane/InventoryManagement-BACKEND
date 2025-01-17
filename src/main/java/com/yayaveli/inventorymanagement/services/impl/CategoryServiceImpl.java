package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Category;
import com.yayaveli.inventorymanagement.repositories.CategoryRepository;
import com.yayaveli.inventorymanagement.services.CategoryService;
import com.yayaveli.inventorymanagement.validators.CategoryValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if (!errors.isEmpty()) {
            System.out.println(errors);
            System.out.println(ErrorCodes.CATEGORY_NOT_VALID);
            log.error("Category not valid {}", categoryDto);
            throw new InvalidEntityException("La catégorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDto.fromEntity(categoryRepository.save(CategoryDto.toEntity(categoryDto)));
    }

    @Override
    public CategoryDto findById(Integer id) {
        if (id == null) {
            log.error("Category id is null");
            return null;
        }
        return categoryRepository.findById(id).map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune catégorie avec l'id = " + id,
                        ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public CategoryDto findByCategoryCode(String categoryCode) {
        if (!StringUtils.hasLength(categoryCode)) {
            log.error("categoryCode is null");
            return null;
        }
        return categoryRepository.findByCategoryCode(categoryCode).map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune catégorie avec le code = " + categoryCode,
                        ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Category is null");
            return;
        }
        if(!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Aucune catégorie avec l'id = " + id,
                    ErrorCodes.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

}
