package com.yayaveli.inventorymanagement.dto;

import java.time.Instant;
import java.util.List;

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
    private Integer companyId;
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
                .companyId(providerOrder.getCompanyId())
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
        providerOrder.setCompanyId(providerOrderDto.getCompanyId());
        providerOrder.setProvider(ProviderDto.toEntity(providerOrderDto.getProviderDto()));

        return providerOrder;
    };
}
