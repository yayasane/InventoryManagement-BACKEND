package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.ProviderApi;
import com.yayaveli.inventorymanagement.dto.ProviderDto;
import com.yayaveli.inventorymanagement.services.ProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController implements ProviderApi {
    private ProviderService providerService;
    // Field Injection
    /*
     * @Autowired
     * private ProviderService providerService;
     */

    // Setter Injection
    /*
     * @Autowired
     * public void setProviderService(ProviderService providerService) {
     * this.providerService = providerService;
     * }
     */

    // Constructor Injection
    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    public ProviderDto save(ProviderDto providerDto) {
        return this.providerService.save(providerDto);
    }

    @Override
    public ProviderDto findById(Integer id) {
        return this.providerService.findById(id);
    }

    @Override
    public List<ProviderDto> findAll() {
        return this.providerService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.providerService.delete(id);

    }
}
