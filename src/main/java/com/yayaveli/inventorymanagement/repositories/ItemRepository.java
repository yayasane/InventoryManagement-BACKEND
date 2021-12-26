package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.Item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
