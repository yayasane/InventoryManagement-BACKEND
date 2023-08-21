package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.ItemDto;

import org.springframework.util.StringUtils;

public class ItemValidator {

    public static List<String> validate(ItemDto itemDto) {
        List<String> errors = new ArrayList<>();

        if (itemDto == null) {
            errors.add("Veuillez renseigner le code de l'article");
            errors.add("Veuillez renseigner le prix unitaire HT de l'article");
            errors.add("Veuillez renseigner le TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
            errors.add("Veuillez sélectionner une catégorie");
            return errors;
        }

        if (!StringUtils.hasLength(itemDto.getItemCode())) {
            errors.add("Veuillez renseigner le code de l'article");
        }
        if (itemDto.getUnitPriceExclT() == null) {
            errors.add("Veuillez renseigner le prix unitaire HT de l'article");
        }
        if (itemDto.getVat() == null) {
            errors.add("Veuillez renseigner le TVA de l'article");
        }
        if (itemDto.getUnitPriceInclT() == null) {
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        }
        if (itemDto.getCategoryDto() == null) {
            errors.add("Veuillez sélectionner une catégorie");
        }

        return errors;
    }

}
