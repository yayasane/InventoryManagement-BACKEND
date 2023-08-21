package com.yayaveli.inventorymanagement.repositories;

import java.util.Optional;

import com.yayaveli.inventorymanagement.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryCode(String categoryCode);
}
