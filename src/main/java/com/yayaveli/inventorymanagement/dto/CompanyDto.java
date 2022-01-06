package com.yayaveli.inventorymanagement.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayaveli.inventorymanagement.models.Company;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDto {
    private Integer id;
    private String name;
    private String description;
    private AddressDto address;
    private String taxCode;
    private String picture;
    private String email;
    private String phoneNumber;
    private String webSite;
    @JsonIgnore
    private List<UserDto> users;

    public static CompanyDto fromEntity(Company company) {
        if (company == null) {
            return null;
            // TODO throw an exception

        }
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .taxCode(company.getTaxCode())
                .picture(company.getPicture())
                .email(company.getEmail())
                .phoneNumber(company.getPhoneNumber())
                .address(AddressDto.fromEntity(company.getAddress()))
                .webSite(company.getWebSite())
                .build();
    };

    public static Company toEntity(CompanyDto companyDto) {
        if (companyDto == null) {
            return null;
            // TODO throw an exception

        }
        Company company = new Company();
        company.setId(companyDto.getId());
        company.setName(companyDto.getName());
        company.setDescription(companyDto.getDescription());
        company.setTaxCode(companyDto.getTaxCode());
        company.setPicture(companyDto.getPicture());
        company.setEmail(companyDto.getEmail());
        company.setPhoneNumber(companyDto.getPhoneNumber());
        company.setAddress(AddressDto.toEntity(companyDto.getAddress()));
        company.setWebSite(companyDto.getWebSite());

        return company;
    };
}
