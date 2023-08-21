package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.ClientApi;
import com.yayaveli.inventorymanagement.dto.ClientDto;
import com.yayaveli.inventorymanagement.services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController implements ClientApi {
    private ClientService clientService;
    // Field Injection
    /*
     * @Autowired
     * private ClientService clientService;
     */

    // Setter Injection
    /*
     * @Autowired
     * public void setClientService(ClientService clientService) {
     * this.clientService = clientService;
     * }
     */

    // Constructor Injection
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public ClientDto save(ClientDto clientDto) {
        return this.clientService.save(clientDto);
    }

    @Override
    public ClientDto findById(Integer id) {
        return this.clientService.findById(id);
    }

    @Override
    public List<ClientDto> findAll() {
        return this.clientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.clientService.delete(id);

    }
}
