package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.InventoryMovement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryManagementRepository extends JpaRepository<InventoryMovement, Integer> {

}
