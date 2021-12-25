package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.UserDto;

import org.springframework.util.StringUtils;

public class UserValidator {
    public static List<String> validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();

        if (userDto == null) {
            errors.add("Veuillez renseigner le prénom de l'utilisateur");
            errors.add("Veuillez renseigner le nom de l'utilisateur");
            errors.add("Veuillez renseigner l'email de l'utilisateur");
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
            errors.add("Veuillez renseigner l'adresse de l'utilisateur");
            errors.add("Veuillez renseigner la date naissance de l'utilisateur");
            return errors;
        }

        if (!StringUtils.hasLength(userDto.getFirstName())) {
            errors.add("Veuillez renseigner le prénom de l'utilisateur");
        }
        if (!StringUtils.hasLength(userDto.getLastName())) {
            errors.add("Veuillez renseigner le nom de l'utilisateur");
        }
        if (!StringUtils.hasLength(userDto.getEmail())) {
            errors.add("Veuillez renseigner l'email de l'utilisateur");
        }
        if (!StringUtils.hasLength(userDto.getPassword())) {
            errors.add("Veuillez renseigner le mot de passe de l'utilisateur");
        }
        if (userDto.getDateOfBirth() == null) {
            errors.add("Veuillez renseigner la de naissance de l'utilisateur");
        }
        if (userDto.getAddressDto() == null) {
            errors.add("Veuillez renseigner l'adresse de l'utilisateur");
        } else {
            if (!StringUtils.hasLength(userDto.getAddressDto().getAddress1())) {
                errors.add("Le champs 'Adresse 1' est obligatoire");
            }
            if (!StringUtils.hasLength(userDto.getAddressDto().getCity())) {
                errors.add("Le champs 'Ville' est obligatoire");
            }
            if (!StringUtils.hasLength(userDto.getAddressDto().getZipCode())) {
                errors.add("Le champs 'Code Postale' est obligatoire");
            }
            if (!StringUtils.hasLength(userDto.getAddressDto().getCountry())) {
                errors.add("Le champs 'Pays' est obligatoire");
            }

        }

        return errors;
    }
}
