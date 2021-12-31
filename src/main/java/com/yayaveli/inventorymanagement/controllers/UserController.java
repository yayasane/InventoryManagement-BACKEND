package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.UserApi;
import com.yayaveli.inventorymanagement.dto.UserDto;
import com.yayaveli.inventorymanagement.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {
    private UserService userService;
    // Field Injection
    /*
     * @Autowired
     * private UserService userService;
     */

    // Setter Injection
    /*
     * @Autowired
     * public void setUserService(UserService userService) {
     * this.userService = userService;
     * }
     */

    // Constructor Injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserDto save(UserDto userDto) {
        return this.userService.save(userDto);
    }

    @Override
    public UserDto findById(Integer id) {
        return this.userService.findById(id);
    }

    @Override
    public List<UserDto> findAll() {
        return this.userService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.userService.delete(id);

    }
}
