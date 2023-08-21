package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.ProviderDto;

import org.springframework.util.StringUtils;

public class ProviderValidator {
    public static List<String> validate(ProviderDto providerDto) {
        List<String> errors = new ArrayList<>();

        if (providerDto == null) {
            errors.add("Veuillez renseigner le prénom du provider");
            errors.add("Veuillez renseigner le nom du provider");
            errors.add("Veuillez renseigner l'email du provider");
            errors.add("Veuillez renseigner le numéro de téléphone du provider");
            return errors;
        }

        if (!StringUtils.hasLength(providerDto.getFirstName())) {
            errors.add("Veuillez renseigner le prénom du provider");
        }
        if (!StringUtils.hasLength(providerDto.getLastName())) {
            errors.add("Veuillez renseigner le nom du provider");
        }
        if (!StringUtils.hasLength(providerDto.getEmail())) {
            errors.add("Veuillez renseigner l'email du provider");
        }
        if (!StringUtils.hasLength(providerDto.getPhoneNumber())) {
            errors.add("Veuillez renseigner le numéro de téléphone du provider");
        }

        return errors;
    }
}
