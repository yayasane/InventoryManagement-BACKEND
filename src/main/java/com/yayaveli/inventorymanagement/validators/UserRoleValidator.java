package com.yayaveli.inventorymanagement.validators;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.dto.UserRoleDto;

import org.springframework.util.StringUtils;

public class UserRoleValidator {
    public static List<String> validate(UserRoleDto clientOrderDto) {
        List<String> errors = new ArrayList<>();

        if (clientOrderDto == null) {
            errors.add("Veuillez renseigner le name du role");
            errors.add("Veuillez renseigner l'utilsateur du role");
            return errors;
        }

        if (!StringUtils.hasLength(clientOrderDto.getRoleName())) {
            errors.add("Veuillez renseigner le name du role");
        }
        if (clientOrderDto.getUserDto() == null) {
            errors.add("Veuillez renseigner l'utilsateur du role");
        }

        return errors;
    }
}
