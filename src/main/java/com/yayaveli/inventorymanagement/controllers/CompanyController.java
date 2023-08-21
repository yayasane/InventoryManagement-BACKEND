package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.CompanyApi;
import com.yayaveli.inventorymanagement.dto.CompanyDto;
import com.yayaveli.inventorymanagement.services.CompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController implements CompanyApi {
    private CompanyService companyService;
    // Field Injection
    /*
     * @Autowired
     * private CompanyService companyService;
     */

    // Setter Injection
    /*
     * @Autowired
     * public void setCompanyService(CompanyService companyService) {
     * this.companyService = companyService;
     * }
     */

    // Constructor Injection
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    public CompanyDto save(CompanyDto companyDto) {
        return this.companyService.save(companyDto);
    }

    @Override
    public CompanyDto findById(Integer id) {
        return this.companyService.findById(id);
    }

    @Override
    public List<CompanyDto> findAll() {
        return this.companyService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.companyService.delete(id);

    }
}
