package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ProviderDto;

public interface ProviderService {
    ProviderDto save(ProviderDto providerDto);

    ProviderDto findById(Integer id);

    List<ProviderDto> findAll();

    void deleteInteger(Integer id);
}
