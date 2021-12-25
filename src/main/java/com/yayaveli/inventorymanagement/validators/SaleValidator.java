package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.SaleDto;

import org.springframework.util.StringUtils;

public class SaleValidator {
    public static List<String> validate(SaleDto userDto) {
        List<String> errors = new ArrayList<>();

        if (userDto == null) {
            errors.add("Veuillez renseigner le code de vente");
            errors.add("Veuillez renseigner l'adresse de vente");
            return errors;
        }

        if (!StringUtils.hasLength(userDto.getSaleCode())) {
            errors.add("Veuillez renseigner le code de la vente");
        }
        if (userDto.getSaleDate() == null) {
            errors.add("Veuillez renseigner l'adresse de vente");
        }

        return errors;
    }
}
