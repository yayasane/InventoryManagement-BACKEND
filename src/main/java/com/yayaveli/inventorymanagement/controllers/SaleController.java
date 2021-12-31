package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.SaleApi;
import com.yayaveli.inventorymanagement.dto.SaleDto;
import com.yayaveli.inventorymanagement.services.SaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleController implements SaleApi {
    private SaleService saleService;
    // Field Injection
    /*
     * @Autowired
     * private SaleService saleService;
     */

    // Setter Injection
    /*
     * @Autowired
     * public void setSaleService(SaleService saleService) {
     * this.saleService = saleService;
     * }
     */

    // Constructor Injection
    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    public SaleDto save(SaleDto saleDto) {
        return this.saleService.save(saleDto);
    }

    @Override
    public SaleDto findById(Integer id) {
        return this.saleService.findById(id);
    }

    @Override
    public List<SaleDto> findAll() {
        return this.saleService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.saleService.delete(id);

    }
}
