package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.CompanyDto;

public interface CompanyService {
    CompanyDto save(CompanyDto companyDto);

    CompanyDto findById(Integer id);

    CompanyDto findByCompanyCode(String companyCode);

    List<CompanyDto> findAll();

    void delete(Integer id);
}
