package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.ClientOrderApi;
import com.yayaveli.inventorymanagement.dto.ClientOrderDto;
import com.yayaveli.inventorymanagement.services.ClientOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientOrderController implements ClientOrderApi {

    private ClientOrderService clientOrderService;

    @Autowired
    public ClientOrderController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @Override
    public ResponseEntity<ClientOrderDto> save(ClientOrderDto clientOrderDto) {
        return ResponseEntity.ok(clientOrderService.save(clientOrderDto));
        // return
        // ResponseEntity.status(HttpStatus.ACCEPTED).body(clientOrderService.save(clientOrderDto));
    }

    @Override
    public ResponseEntity<ClientOrderDto> findById(Integer id) {
        return ResponseEntity.ok(clientOrderService.findById(id));
    }

    @Override
    public ResponseEntity<ClientOrderDto> findByOrderCode(String OrderCode) {
        return ResponseEntity.ok(clientOrderService.findByOrderCode(OrderCode));

    }

    @Override
    public ResponseEntity<List<ClientOrderDto>> findAll() {
        return ResponseEntity.ok(clientOrderService.findAll());
    }

    @Override
    public ResponseEntity delete(Integer id) {
        clientOrderService.delete(id);
        return ResponseEntity.ok().build();
    }

}
