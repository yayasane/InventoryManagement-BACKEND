package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.ClientOrderDto;

import org.springframework.util.StringUtils;

public class ClientOrderValidator {
    public static List<String> validate(ClientOrderDto clientOrderDto) {
        List<String> errors = new ArrayList<>();

        if (clientOrderDto == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date la commande");
            errors.add("Veuillez renseigner le client de la commande");
            return errors;
        }

        if (!StringUtils.hasLength(clientOrderDto.getOrderCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (clientOrderDto.getOrderDate() == null) {
            errors.add("Veuillez renseigner la date la commande");
        }
        if (clientOrderDto.getClientDto() == null) {
            errors.add("Veuillez renseigner le client de la commande");
        }

        return errors;
    }
}
