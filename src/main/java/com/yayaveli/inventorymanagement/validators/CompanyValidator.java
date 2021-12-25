package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.CompanyDto;

import org.springframework.util.StringUtils;

public class CompanyValidator {
    public static List<String> validate(CompanyDto companyDto) {
        List<String> errors = new ArrayList<>();

        if (companyDto == null) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
            errors.add("Veuillez renseigner le numéro de téléphone de l'entreprise");
            errors.add("Veuillez renseigner la description de l'entreprise");
            errors.add("Veuillez renseigner l'email de l'entreprise");
            errors.add("Veuillez renseigner l'adresse de l'entreprise");
            return errors;
        }

        if (!StringUtils.hasLength(companyDto.getName())) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
        }
        if (!StringUtils.hasLength(companyDto.getPhoneNumber())) {
            errors.add("Veuillez renseigner le numéro de téléphone de l'entreprise");
        }
        if (!StringUtils.hasLength(companyDto.getEmail())) {
            errors.add("Veuillez renseigner l'email de l'entreprise");
        }
        if (!StringUtils.hasLength(companyDto.getDescription())) {
            errors.add("Veuillez renseigner la description de l'entreprise");
        }
        if (companyDto.getAddressDto() == null) {
            errors.add("Veuillez renseigner l'adresse de l'entreprise");
        } else {
            if (!StringUtils.hasLength(companyDto.getAddressDto().getAddress1())) {
                errors.add("Le champs 'Adresse 1' est obligatoire");
            }
            if (!StringUtils.hasLength(companyDto.getAddressDto().getCity())) {
                errors.add("Le champs 'Ville' est obligatoire");
            }
            if (!StringUtils.hasLength(companyDto.getAddressDto().getZipCode())) {
                errors.add("Le champs 'Code Postale' est obligatoire");
            }
            if (!StringUtils.hasLength(companyDto.getAddressDto().getCountry())) {
                errors.add("Le champs 'Pays' est obligatoire");
            }

        }

        return errors;
    }
}
