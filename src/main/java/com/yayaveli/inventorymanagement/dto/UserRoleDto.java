package com.yayaveli.inventorymanagement.dto;

import com.yayaveli.inventorymanagement.models.UserRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRoleDto {
    private Integer id;
    private String roleName;
    private UserDto userDto;

    public static UserRoleDto fromEntity(UserRole userRole) {
        if (userRole == null) {
            return null;
            // TODO throw an exception

        }
        return UserRoleDto.builder()
                .id(userRole.getId())
                .roleName(userRole.getRoleName())
                .build();
    };

    public static UserRole toEntity(UserRoleDto userRoleDto) {
        if (userRoleDto == null) {
            return null;
            // TODO throw an exception

        }
        UserRole userRole = new UserRole();
        userRole.setId(userRoleDto.getId());
        userRole.setRoleName(userRoleDto.getRoleName());
        userRole.setUser(UserDto.toEntity(userRoleDto.getUserDto()));

        return userRole;
    };
}
