package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.ItemDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
import com.yayaveli.inventorymanagement.services.ItemService;
import com.yayaveli.inventorymanagement.validators.ItemValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemDto save(ItemDto itemDto) {
        List<String> errors = ItemValidator.validate(itemDto);
        if (!errors.isEmpty()) {
            log.error("Item i not valid {}", itemDto);
            throw new InvalidEntityException("L'artcle n'est pas valide", ErrorCodes.ITEM_NOT_VALID, errors);
        }
        return ItemDto.fromEntity(itemRepository.save(ItemDto.toEntity(itemDto)));
    }

    @Override
    public ItemDto findById(Integer id) {
        if (id == null) {
            log.error("Item is null");
            return null;
        }
        Optional<Item> item = itemRepository.findById(id);

        ItemDto itemDto = ItemDto.fromEntity(item.get());

        return Optional.of(itemDto).orElseThrow(() -> new EntityNotFoundException("Aucun article avec l'id = " + id,
                ErrorCodes.ITEM_NOT_FOUND));
    }

    @Override
    public ItemDto findByItemCode(String itemCode) {
        if (!StringUtils.hasLength(itemCode)) {
            log.error("itemCode is null");
            return null;
        }
        Optional<Item> item = itemRepository.findByItemCode(itemCode);

        ItemDto itemDto = ItemDto.fromEntity(item.get());

        return Optional.of(itemDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec le code = " + itemCode,
                        ErrorCodes.ITEM_NOT_FOUND));
    }

    @Override
    public List<ItemDto> findAll() {
        return itemRepository.findAll().stream().map(ItemDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void deleteInteger(Integer id) {
        if (id == null) {
            log.error("Item is null");
            return;
        }
        itemRepository.deleteById(id);
    }

}
