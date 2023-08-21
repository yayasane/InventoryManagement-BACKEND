package com.yayaveli.inventorymanagement.models;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.yayaveli.inventorymanagement.enums.InventoryMovementType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
public class InventoryMovement extends AbstractEntity {
    private Instant inventoryMovementDate;
    private BigDecimal quantity;
    @ManyToOne
    private Item item;
    private InventoryMovementType inventoryMovementType;
    private Integer companyId;

}
