package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.SaleDto;

public interface SaleService {
    SaleDto save(SaleDto saleDto);

    SaleDto findById(Integer id);

    SaleDto findBySaleCode(String OrderCode);

    List<SaleDto> findAll();

    void delete(Integer id);

}
