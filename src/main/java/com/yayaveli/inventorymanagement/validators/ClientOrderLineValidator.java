package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.ClientOrderLineDto;

public class ClientOrderLineValidator {
    public static List<String> validate(ClientOrderLineDto providerOrderLineDto) {
        List<String> errors = new ArrayList<>();

        if (providerOrderLineDto == null) {
            errors.add("Veuillez renseigner l'article de la ligne de commande");
            errors.add("Veuillez renseigner le commande de la ligne de commande");
            errors.add("Veuillez renseigner le prix unitaire TTC de la ligne de commande");
            errors.add("Veuillez renseigner la quantité de la ligne de commande");
            return errors;
        }

        if (providerOrderLineDto.getItemDto() == null) {
            errors.add("Veuillez renseigner l'article de la ligne de commande");
        }
        if (providerOrderLineDto.getClientOrderDto() == null) {
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
