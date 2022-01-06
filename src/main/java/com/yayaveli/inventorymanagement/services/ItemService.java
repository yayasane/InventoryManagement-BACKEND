package com.yayaveli.inventorymanagement.services;

import java.util.List;

import com.yayaveli.inventorymanagement.dto.ItemDto;

public interface ItemService {
    ItemDto save(ItemDto itemDto);

    ItemDto findById(Integer id);

    ItemDto findByItemCode(String itemCode);

    List<ItemDto> findAll();

    void delete(Integer id);

}
