package com.yayaveli.inventorymanagement.dto;

import java.math.BigDecimal;

import com.yayaveli.inventorymanagement.models.SaleLine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleLineDto {
    private Integer id;
    private SaleDto saleDto;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;

    public static SaleLineDto fromEntity(SaleLine saleLine) {
        if (saleLine == null) {
            return null;
            // TODO throw an exception

        }
        return SaleLineDto.builder()
                .id(saleLine.getId())
                .saleDto(SaleDto.fromEntity(saleLine.getSale()))
                .quantity(saleLine.getQuantity())
                .unitPrice(saleLine.getUnitPrice())
                .build();
    };

    public static SaleLine toEntity(SaleLineDto saleLineDto) {
        if (saleLineDto == null) {
            return null;
            // TODO throw an exception

        }
        SaleLine saleLine = new SaleLine();
        saleLine.setId(saleLineDto.getId());
        saleLine.setSale(SaleDto.toEntity(saleLineDto.getSaleDto()));
        saleLine.setQuantity(saleLineDto.getQuantity());
        saleLine.setUnitPrice(saleLineDto.getUnitPrice());

        return saleLine;
    };
}
