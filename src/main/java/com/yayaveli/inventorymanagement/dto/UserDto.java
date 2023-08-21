package com.yayaveli.inventorymanagement.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.models.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String email;
    private String password;
    private AddressDto addressDto;
    private String picture;
    private String phoneNumber;
    private CompanyDto company;
    private List<UserRoleDto> userRoles;

    public static UserDto fromEntity(User user) {
        if (user == null) {
            return null;
            // TODO throw an exception

        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .picture(user.getPicture())
                .addressDto(AddressDto.fromEntity(user.getAddress()))
                .company(CompanyDto.fromEntity(user.getCompany()))
                .userRoles(
                        user.getUserRoles() != null
                                ? user.getUserRoles().stream().map(UserRoleDto::fromEntity).collect(Collectors.toList())
                                : null)
                .build();
    };

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
            // TODO throw an exception

        }
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPicture(userDto.getPicture());
        user.setCompany(CompanyDto.toEntity(userDto.getCompany()));
        user.setAddress(AddressDto.toEntity(userDto.getAddressDto()));

        return user;
    };
}
