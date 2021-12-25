package com.yayaveli.inventorymanagement.dto;

import java.math.BigDecimal;

import com.yayaveli.inventorymanagement.models.ProviderOrderLine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderOrderLineDto {
    private Integer id;
    private ItemDto itemDto;
    private ProviderOrderDto providerOrderDto;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;

    public static ProviderOrderLineDto fromEntity(ProviderOrderLine providerOrderLine) {
        if (providerOrderLine == null) {
            return null;
            // TODO throw an exception

        }
        return ProviderOrderLineDto.builder()
                .id(providerOrderLine.getId())
                .itemDto(ItemDto.fromEntity(providerOrderLine.getItem()))
                .providerOrderDto(ProviderOrderDto.fromEntity(providerOrderLine.getProviderOrder()))
                .quantity(providerOrderLine.getQuantity())
                .build();
    };

    public static ProviderOrderLine toEntity(ProviderOrderLineDto providerOrderLineDto) {
        if (providerOrderLineDto == null) {
            return null;
            // TODO throw an exception

        }
        ProviderOrderLine providerOrderLine = new ProviderOrderLine();
        providerOrderLine.setId(providerOrderLineDto.getId());
        providerOrderLine.setItem(ItemDto.toEntity(providerOrderLineDto.getItemDto()));
        providerOrderLine.setProviderOrder(ProviderOrderDto.toEntity(providerOrderLineDto.getProviderOrderDto()));
        providerOrderLine.setQuantity(providerOrderLineDto.getQuantity());

        return providerOrderLine;
    };
}
