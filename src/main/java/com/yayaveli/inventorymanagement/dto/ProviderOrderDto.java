package com.yayaveli.inventorymanagement.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayaveli.inventorymanagement.models.ProviderOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderOrderDto {
    private Integer id;
    private String orderCode;
    private Instant orderDate;
    private ProviderDto providerDto;
    @JsonIgnore
    private List<ProviderOrderLineDto> providerOrderLines;

    public static ProviderOrderDto fromEntity(ProviderOrder providerOrder) {
        if (providerOrder == null) {
            return null;
            // TODO throw an exception

        }
        return ProviderOrderDto.builder()
                .id(providerOrder.getId())
                .orderCode(providerOrder.getOrderCode())
                .orderDate(providerOrder.getOrderDate())
                .providerDto(ProviderDto.fromEntity(providerOrder.getProvider()))
                .build();

    };

    public static ProviderOrder toEntity(ProviderOrderDto providerOrderDto) {
        if (providerOrderDto == null) {
            return null;
            // TODO throw an exception

        }
        ProviderOrder providerOrder = new ProviderOrder();
        providerOrder.setId(providerOrderDto.getId());
        providerOrder.setOrderCode(providerOrderDto.getOrderCode());
        providerOrder.setOrderDate(providerOrderDto.getOrderDate());
        providerOrder.setProvider(ProviderDto.toEntity(providerOrderDto.getProviderDto()));

        return providerOrder;
    };
}
