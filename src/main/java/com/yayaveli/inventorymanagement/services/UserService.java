package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.UserDto;

public interface UserService {
    UserDto save(UserDto userDto);

    UserDto findById(Integer id);

    List<UserDto> findAll();

    void delete(Integer id);
}
