package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.ProviderOrderApi;
import com.yayaveli.inventorymanagement.dto.ProviderOrderDto;
import com.yayaveli.inventorymanagement.services.ProviderOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderOrderController implements ProviderOrderApi {
    private ProviderOrderService providerOrderService;

    @Autowired
    public ProviderOrderController(ProviderOrderService providerOrderService) {
        this.providerOrderService = providerOrderService;
    }

    @Override
    public ResponseEntity<ProviderOrderDto> save(ProviderOrderDto providerOrderDto) {
        return ResponseEntity.ok(providerOrderService.save(providerOrderDto));
        // return
        // ResponseEntity.status(HttpStatus.ACCEPTED).body(providerOrderService.save(providerOrderDto));
    }

    @Override
    public ResponseEntity<ProviderOrderDto> findById(Integer id) {
        return ResponseEntity.ok(providerOrderService.findById(id));
    }

    @Override
    public ResponseEntity<ProviderOrderDto> findByOrderCode(String OrderCode) {
        return ResponseEntity.ok(providerOrderService.findByOrderCode(OrderCode));

    }

    @Override
    public ResponseEntity<List<ProviderOrderDto>> findAll() {
        return ResponseEntity.ok(providerOrderService.findAll());
    }

    @Override
    public ResponseEntity delete(Integer id) {
        providerOrderService.delete(id);
        return ResponseEntity.ok().build();
    }
}
