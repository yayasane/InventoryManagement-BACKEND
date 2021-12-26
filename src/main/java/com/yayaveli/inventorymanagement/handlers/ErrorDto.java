package com.yayaveli.inventorymanagement.handlers;

import java.util.ArrayList;
import java.util.List;

import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private Integer httpCode;

    private ErrorCodes code;
    private String message;
    private List<String> errors = new ArrayList<>();
}
