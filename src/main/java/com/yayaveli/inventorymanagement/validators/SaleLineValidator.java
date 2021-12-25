package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.SaleLineDto;

public class SaleLineValidator {
    public static List<String> validate(SaleLineDto saleLineLineDto) {
        List<String> errors = new ArrayList<>();

        if (saleLineLineDto == null) {
            errors.add("Veuillez renseigner le vente de la ligne de vente");
            errors.add("Veuillez renseigner le prix unitaire TTC de la ligne de vente");
            errors.add("Veuillez renseigner la quantité de la ligne de vente");
            return errors;
        }

        if (saleLineLineDto.getSaleDto() == null) {
            errors.add("Veuillez renseigner la vente la ligne de vente");
        }
        if (saleLineLineDto.getUnitPrice() == null) {
            errors.add("Veuillez renseigner le prix unitaire TTC de la ligne de vente");
        }
        if (saleLineLineDto.getQuantity() == null) {
            errors.add("Veuillez renseigner la quantité de la ligne de vente");
        }

        return errors;
    }
}
