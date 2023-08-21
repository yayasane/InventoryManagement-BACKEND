package com.yayaveli.inventorymanagement.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayaveli.inventorymanagement.models.ClientOrderLine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientOrderLineDto {
    private Integer id;
    private ItemDto itemDto;
    @JsonIgnore
    private ClientOrderDto clientOrderDto;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer companyId;

    public static ClientOrderLineDto fromEntity(ClientOrderLine clientOrderLine) {
        if (clientOrderLine == null) {
            return null;
            // TODO throw an exception

        }
        return ClientOrderLineDto.builder()
                .id(clientOrderLine.getId())
                .itemDto(ItemDto.fromEntity(clientOrderLine.getItem()))
                .clientOrderDto(ClientOrderDto.fromEntity(clientOrderLine.getClientOrder()))
                .companyId(clientOrderLine.getCompanyId())
                .quantity(clientOrderLine.getQuantity())
                .build();
    };

    public static ClientOrderLine toEntity(ClientOrderLineDto clientOrderLineDto) {
        if (clientOrderLineDto == null) {
            return null;
            // TODO throw an exception

        }
        ClientOrderLine clientOrderLine = new ClientOrderLine();
        clientOrderLine.setId(clientOrderLineDto.getId());
        clientOrderLine.setItem(ItemDto.toEntity(clientOrderLineDto.getItemDto()));
        clientOrderLine.setClientOrder(ClientOrderDto.toEntity(clientOrderLineDto.getClientOrderDto()));
        clientOrderLine.setCompanyId(clientOrderLineDto.getCompanyId());
        clientOrderLine.setQuantity(clientOrderLineDto.getQuantity());

        return clientOrderLine;
    };
}
