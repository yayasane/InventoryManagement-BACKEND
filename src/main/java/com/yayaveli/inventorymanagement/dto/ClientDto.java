package com.yayaveli.inventorymanagement.dto;

import java.util.List;

import com.yayaveli.inventorymanagement.models.Client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private String email;
    private AddressDto addressDto;
    private String picture;
    private String phoneNumber;
    private Integer companyId;
    private List<ClientOrderDto> clientOrders;

    public static ClientDto fromEntity(Client client) {
        if (client == null) {
            return null;
            // TODO throw an exception

        }
        return ClientDto.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .picture(client.getPicture())
                .companyId(client.getCompanyId())
                .addressDto(AddressDto.fromEntity(client.getAddress()))
                .build();
    };

    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
            // TODO throw an exception

        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setPicture(clientDto.getPicture());
        client.setCompanyId(clientDto.getCompanyId());
        client.setAddress(AddressDto.toEntity(clientDto.getAddressDto()));

        return client;
    };
}
