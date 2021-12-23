package com.yayaveli.inventorymanagement.dto;

import java.time.Instant;

import com.yayaveli.inventorymanagement.models.Sale;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleDto {
    private Integer id;
    private String saleCode;
    private Instant saleDate;
    private String commentary;

    public static SaleDto fromEntity(Sale sale) {
        if (sale == null) {
            return null;
            // TODO throw an exception

        }
        return SaleDto.builder()
                .id(sale.getId())
                .saleCode(sale.getSaleCode())
                .saleDate(sale.getSaleDate())
                .commentary(sale.getCommentary())
                .build();
    };

    public static Sale toEntity(SaleDto saleDto) {
        if (saleDto == null) {
            return null;
            // TODO throw an exception

        }
        Sale sale = new Sale();
        sale.setId(sale.getId());
        sale.setSaleCode(sale.getSaleCode());
        sale.setSaleDate(sale.getSaleDate());
        sale.setCommentary(sale.getCommentary());

        return sale;
    };
}
