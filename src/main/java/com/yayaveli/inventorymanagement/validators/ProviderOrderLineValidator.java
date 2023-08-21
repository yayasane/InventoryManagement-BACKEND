package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.ProviderOrderLineDto;

public class ProviderOrderLineValidator {
    public static List<String> validate(ProviderOrderLineDto providerOrderLineDto) {
        List<String> errors = new ArrayList<>();

        if (providerOrderLineDto == null) {
            errors.add("Veuillez renseigner l'article la ligne de commande");
            errors.add("Veuillez renseigner le commande de la ligne de commande");
            errors.add("Veuillez renseigner le prix unitaire TTC de la ligne de commande");
            errors.add("Veuillez renseigner la quantité de la ligne de commande");
            return errors;
        }

        if (providerOrderLineDto.getItemDto() == null) {
            errors.add("Veuillez renseigner l'article la ligne de commande");
        }
        if (providerOrderLineDto.getProviderOrderDto() == null) {
            errors.add("Veuillez renseigner le commande de la ligne de commande");
        }
        if (providerOrderLineDto.getUnitPrice() == null) {
            errors.add("Veuillez renseigner le prix unitaire TTC de la ligne de commande");
        }
        if (providerOrderLineDto.getQuantity() == null) {
            errors.add("Veuillez renseigner la quantité de la ligne de commande");
        }

        return errors;
    }
}
