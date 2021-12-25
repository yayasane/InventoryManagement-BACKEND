package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.ProviderOrderDto;

import org.springframework.util.StringUtils;

public class ProviderOrderValidator {
    public static List<String> validate(ProviderOrderDto providerOrderDto) {
        List<String> errors = new ArrayList<>();

        if (providerOrderDto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date la commande");
            errors.add("Veuillez renseigner le provider de la commande");
            return errors;
        }

        if (!StringUtils.hasLength(providerOrderDto.getOrderCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (providerOrderDto.getOrderDate() == null) {
            errors.add("Veuillez renseigner la date la commande");
        }
        if (providerOrderDto.getProviderDto() == null) {
            errors.add("Veuillez renseigner le fournisseur de la commande");
        }

        return errors;
    }
}
