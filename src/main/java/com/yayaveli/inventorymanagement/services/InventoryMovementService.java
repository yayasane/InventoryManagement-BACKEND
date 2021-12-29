package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.InventoryMovementDto;

public interface InventoryMovementService {
    InventoryMovementDto save(InventoryMovementDto clientDto);

    InventoryMovementDto findById(Integer id);

    List<InventoryMovementDto> findAll();

    void deleteInteger(Integer id);
}
