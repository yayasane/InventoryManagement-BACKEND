package com.yayaveli.inventorymanagement.controllers;

import java.util.List;

import com.yayaveli.inventorymanagement.controllers.api.ItemApi;
import com.yayaveli.inventorymanagement.dto.ItemDto;
import com.yayaveli.inventorymanagement.services.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController implements ItemApi {

    private ItemService itemService;
    // Field Injection
    /*
     * @Autowired
     * private ItemService itemService;
     */

    // Setter Injection
    /*
     * @Autowired
     * public void setItemService(ItemService itemService) {
     * this.itemService = itemService;
     * }
     */

    // Constructor Injection
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public ItemDto save(ItemDto itemDto) {
        return this.itemService.save(itemDto);
    }

    @Override
    public ItemDto findById(Integer id) {
        return this.itemService.findById(id);
    }

    @Override
    public ItemDto findByItemCode(String itemCode) {
        return this.itemService.findByItemCode(itemCode);
    }

    @Override
    public List<ItemDto> findAll() {
        return this.itemService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.itemService.deleteInteger(id);

    }

}
