package com.yayaveli.inventorymanagement.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.yayaveli.inventorymanagement.enums.InventoryMovementType;
import com.yayaveli.inventorymanagement.models.InventoryMovement;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryMovementDto {
    private Integer id;
    private Instant inventoryMovementDate;
    private BigDecimal quantity;
    private ItemDto item;
    private Integer companyId;
    private InventoryMovementType inventoryMovementType;

    public static InventoryMovementDto fromEntity(InventoryMovement inventoryMovement) {
        if (inventoryMovement == null) {
            return null;
            // TODO throw an exception

        }
        return InventoryMovementDto.builder()
                .id(inventoryMovement.getId())
                .inventoryMovementDate(inventoryMovement.getInventoryMovementDate())
                .quantity(inventoryMovement.getQuantity())
                .companyId(inventoryMovement.getCompanyId())
                .item(ItemDto.fromEntity(inventoryMovement.getItem()))
                .build();

    };

    public static InventoryMovement toEntity(InventoryMovementDto inventoryMovementDto) {
        if (inventoryMovementDto == null) {
            return null;
            // TODO throw an exception

        }
        InventoryMovement inventoryMovement = new InventoryMovement();
        inventoryMovement.setId(inventoryMovementDto.getId());
        inventoryMovement.setId(inventoryMovementDto.getId());
        inventoryMovement.setInventoryMovementDate(inventoryMovementDto.getInventoryMovementDate());
        inventoryMovement.setQuantity(inventoryMovementDto.getQuantity());
        inventoryMovement.setCompanyId(inventoryMovementDto.getCompanyId());
        inventoryMovement.setItem(ItemDto.toEntity(inventoryMovementDto.getItem()));

        return inventoryMovement;
    };
}
