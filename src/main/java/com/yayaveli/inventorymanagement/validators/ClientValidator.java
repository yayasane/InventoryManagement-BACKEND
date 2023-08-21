package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.ClientDto;

import org.springframework.util.StringUtils;

public class ClientValidator {
    public static List<String> validate(ClientDto clientDto) {
        List<String> errors = new ArrayList<>();

        if (clientDto == null) {
            errors.add("Veuillez renseigner le prénom du client");
            errors.add("Veuillez renseigner le nom du client");
            errors.add("Veuillez renseigner l'email du client");
            errors.add("Veuillez renseigner le numéro de téléphone du client");
            return errors;
        }

        if (!StringUtils.hasLength(clientDto.getFirstName())) {
            errors.add("Veuillez renseigner le prénom du client");
        }
        if (!StringUtils.hasLength(clientDto.getLastName())) {
            errors.add("Veuillez renseigner le nom du client");
        }
        if (!StringUtils.hasLength(clientDto.getEmail())) {
            errors.add("Veuillez renseigner l'email du client");
        }
        if (!StringUtils.hasLength(clientDto.getPhoneNumber())) {
            errors.add("Veuillez renseigner le numéro de téléphone du client");
        }

        return errors;
    }
}
