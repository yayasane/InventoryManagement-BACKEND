package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ProviderOrderDto;

public interface ProviderOrderService {
    ProviderOrderDto save(ProviderOrderDto providerOrderDto);

    ProviderOrderDto findById(Integer id);

    ProviderOrderDto findByOrderCode(String OrderCode);

    List<ProviderOrderDto> findAll();

    void delete(Integer id);
}
