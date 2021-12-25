package com.yayaveli.inventorymanagement.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayaveli.inventorymanagement.models.ClientOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientOrderDto {
    private Integer id;
    private String orderCode;
    private Instant orderDate;
    private ClientDto clientDto;
    private Integer companyId;
    @JsonIgnore
    private List<ClientOrderLineDto> clientOrderLines;

    public static ClientOrderDto fromEntity(ClientOrder clientOrder) {
        if (clientOrder == null) {
            return null;
            // TODO throw an exception

        }
        return ClientOrderDto.builder()
                .id(clientOrder.getId())
                .orderCode(clientOrder.getOrderCode())
                .orderDate(clientOrder.getOrderDate())
                .clientDto(ClientDto.fromEntity(clientOrder.getClient()))
                .build();

    };

    public static ClientOrder toEntity(ClientOrderDto clientOrderDto) {
        if (clientOrderDto == null) {
            return null;
            // TODO throw an exception

        }
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setId(clientOrderDto.getId());
        clientOrder.setOrderCode(clientOrderDto.getOrderCode());
        clientOrder.setOrderDate(clientOrderDto.getOrderDate());
        clientOrder.setClient(ClientDto.toEntity(clientOrderDto.getClientDto()));

        return clientOrder;
    };
}
