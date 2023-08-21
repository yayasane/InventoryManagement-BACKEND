package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ClientOrderDto;

public interface ClientOrderService {
    ClientOrderDto save(ClientOrderDto clientOrderDto);

    ClientOrderDto findById(Integer id);

    ClientOrderDto findByOrderCode(String OrderCode);

    List<ClientOrderDto> findAll();

    void delete(Integer id);

}
