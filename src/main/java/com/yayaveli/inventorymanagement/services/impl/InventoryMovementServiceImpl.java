package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.ClientDto;
import com.yayaveli.inventorymanagement.dto.InventoryMovementDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.InventoryMovement;
import com.yayaveli.inventorymanagement.repositories.InventoryMovementRepository;
import com.yayaveli.inventorymanagement.services.InventoryMovementService;
import com.yayaveli.inventorymanagement.validators.InventoryMovementValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryMovementServiceImpl implements InventoryMovementService {

    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    public InventoryMovementServiceImpl(InventoryMovementRepository inventoryMovementRepository) {
        this.inventoryMovementRepository = inventoryMovementRepository;
    }

    @Override
    public InventoryMovementDto save(InventoryMovementDto inventoryMovementDto) {
        List<String> errors = InventoryMovementValidator.validate(inventoryMovementDto);
        if (!errors.isEmpty()) {
            log.error("InventoryMovement is not valid {}", inventoryMovementDto);
            throw new InvalidEntityException("Le mouvement de stock n'est pas valide", ErrorCodes.INVENTORY_MOVEMENT_NOT_VALID,
                    errors);
        }
        return InventoryMovementDto
                .fromEntity(inventoryMovementRepository.save(InventoryMovementDto.toEntity(inventoryMovementDto)));
    }

    @Override
    public InventoryMovementDto findById(Integer id) {
        if (id == null) {
            log.error("InventoryMovement id is null");
            return null;
        }
        return inventoryMovementRepository.findById(id).map(InventoryMovementDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun mouvement de stock avec l'id = " + id,
                        ErrorCodes.INVENTORY_MOVEMENT_NOT_FOUND));
    }

    @Override
    public List<InventoryMovementDto> findAll() {
        return inventoryMovementRepository.findAll().stream().map(InventoryMovementDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("InventoryMovement is is null");
            return;
        }
        if(!inventoryMovementRepository.existsById(id)) {
            throw new EntityNotFoundException("Aucun mouvement de stock avec l'id = " + id,
                    ErrorCodes.INVENTORY_MOVEMENT_NOT_FOUND);
        }
        inventoryMovementRepository.deleteById(id);
    }

}
