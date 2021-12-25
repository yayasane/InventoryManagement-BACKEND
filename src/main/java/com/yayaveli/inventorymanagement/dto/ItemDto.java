package com.yayaveli.inventorymanagement.dto;

import java.math.BigDecimal;

import com.yayaveli.inventorymanagement.models.Item;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemDto {
    private Integer id;
    private String itemCode;
    private String designation;
    private BigDecimal unitPriceExclT;
    private BigDecimal vat;
    private BigDecimal unitPriceInclT;
    private String picture;
    private Integer companyId;
    private CategoryDto categoryDto;

    public static ItemDto fromEntity(Item item) {
        if (item == null) {
            return null;
            // TODO throw an exception

        }
        return ItemDto.builder()
                .id(item.getId())
                .itemCode(item.getItemCode())
                .designation(item.getDesignation())
                .unitPriceExclT(item.getUnitPriceExclT())
                .vat(item.getVat())
                .unitPriceInclT(item.getUnitPriceInclT())
                .picture(item.getPicture())
                .categoryDto(CategoryDto.fromEntity(item.getCategory()))
                .build();
    };

    public static Item toEntity(ItemDto itemDto) {
        if (itemDto == null) {
            return null;
            // TODO throw an exception

        }
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setItemCode(itemDto.getItemCode());
        item.setDesignation(itemDto.getDesignation());
        item.setUnitPriceExclT(itemDto.getUnitPriceExclT());
        item.setVat(itemDto.getVat());
        item.setUnitPriceInclT(itemDto.getUnitPriceInclT());
        item.setPicture(itemDto.getPicture());
        item.setCategory(CategoryDto.toEntity(itemDto.getCategoryDto()));

        return item;
    };
}
