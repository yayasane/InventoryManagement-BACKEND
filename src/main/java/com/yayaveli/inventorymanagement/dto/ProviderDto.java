package com.yayaveli.inventorymanagement.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayaveli.inventorymanagement.models.Provider;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private String email;
    private AddressDto addressDto;
    private String picture;
    private String phoneNumber;
    @JsonIgnore
    private List<ProviderOrderDto> providerOrders;

    public static ProviderDto fromEntity(Provider provider) {
        if (provider == null) {
            return null;
            // TODO throw an exception

        }
        return ProviderDto.builder()
                .id(provider.getId())
                .firstName(provider.getFirstName())
                .lastName(provider.getLastName())
                .email(provider.getEmail())
                .phoneNumber(provider.getPhoneNumber())
                .picture(provider.getPicture())
                .addressDto(AddressDto.fromEntity(provider.getAddress()))
                .build();
    };

    public static Provider toEntity(ProviderDto providerDto) {
        if (providerDto == null) {
            return null;
            // TODO throw an exception

        }
        Provider provider = new Provider();
        provider.setId(providerDto.getId());
        provider.setFirstName(providerDto.getFirstName());
        provider.setLastName(providerDto.getLastName());
        provider.setEmail(providerDto.getEmail());
        provider.setPhoneNumber(providerDto.getPhoneNumber());
        provider.setPicture(providerDto.getPicture());
        provider.setAddress(AddressDto.toEntity(providerDto.getAddressDto()));

        return provider;
    };
}
