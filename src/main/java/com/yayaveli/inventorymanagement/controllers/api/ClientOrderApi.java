package com.yayaveli.inventorymanagement.controllers.api;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ClientOrderDto;
import com.yayaveli.inventorymanagement.utils.Constants;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;

@Api(Constants.APP_ROOT + "/clientOrders")
public interface ClientOrderApi {

    @PostMapping(Constants.APP_ROOT + "/clientOrders")
    ResponseEntity<ClientOrderDto> save(@RequestBody ClientOrderDto clientOrderDto);

    @GetMapping(Constants.APP_ROOT + "/clientOrders/{id}")
    ResponseEntity<ClientOrderDto> findById(Integer id);

    @GetMapping(Constants.APP_ROOT + "/clientOrders/{OrderCode}")
    ResponseEntity<ClientOrderDto> findByOrderCode(@PathVariable(name = "OrderCode") String orderCode);

    @GetMapping(Constants.APP_ROOT + "/clientOrders")
    ResponseEntity<List<ClientOrderDto>> findAll();

    @DeleteMapping(Constants.APP_ROOT + "/clientsOrders/{id}")
    ResponseEntity delete(@PathVariable Integer id);

}