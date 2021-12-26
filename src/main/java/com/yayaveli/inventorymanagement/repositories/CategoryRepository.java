package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Item, Integer> {
}
