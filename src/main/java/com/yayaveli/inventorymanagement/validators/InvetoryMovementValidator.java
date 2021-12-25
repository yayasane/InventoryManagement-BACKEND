package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.InventoryMovementDto;

public class InvetoryMovementValidator {
    public static List<String> validate(InventoryMovementDto providerOrderLineDto) {
        List<String> errors = new ArrayList<>();

        if (providerOrderLineDto == null) {
            errors.add("Veuillez renseigner la date du mouvement de stock");
            errors.add("Veuillez renseigner le type du mouvement de stock");
            errors.add("Veuillez renseigner l'article du mouvement de stock");
            errors.add("Veuillez renseigner la quantité du mouvement de stock");
            return errors;
        }

        if (providerOrderLineDto.getInventoryMovementDate() == null) {
            errors.add("Veuillez renseigner la date du mouvement de stock");
        }
        if (providerOrderLineDto.getInventoryMovementType() == null) {
            errors.add("Veuillez renseigner le type du mouvement de stock");
        }
        if (providerOrderLineDto.getItem() == null) {
            errors.add("Veuillez renseigner l'article du mouvement de stock");
        }
        if (providerOrderLineDto.getQuantity() == null) {
            errors.add("Veuillez renseigner la quantité du mouvement de stock");
        }

        return errors;
    }
}
