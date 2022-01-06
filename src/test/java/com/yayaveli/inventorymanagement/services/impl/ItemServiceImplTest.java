package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.AddressDto;
import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.dto.ItemDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
import com.yayaveli.inventorymanagement.validators.ItemValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Captor
    ArgumentCaptor<Item> itemArgumentCaptor;

    private ItemServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ItemServiceImpl(itemRepository);
    }

    @Test
    void givenItemDto_whenAddItem_thenShouldSaveSuccess() {
        //Given
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryCode("Cat test")
                .designation("Designation test")
                .build();

        ItemDto itemDto = ItemDto.
                builder()
                .itemCode(UUID.randomUUID().toString())
                .designation("Item 1")
                .unitPriceExclT(new BigDecimal(2000))
                .vat(new BigDecimal("0.1"))
                .unitPriceInclT(new BigDecimal(1800))
                .picture("")
                .companyId(3)
                .categoryDto(categoryDto)
                .build();

        //When
        underTest.save(itemDto);

        //Then
        assertThat(ItemValidator.validate(itemDto)).isEqualTo(Collections.emptyList());
        then(itemRepository).should().save(itemArgumentCaptor.capture());
        Item capturedItem = itemArgumentCaptor.getValue();
        assertThat(capturedItem).isEqualTo(ItemDto.toEntity(itemDto));
        then(itemRepository).should().save(ItemDto.toEntity(itemDto));

    }

    @Test
    void givenItemDto_whenAddItem_thenShouldThrowInvalidEntityException(){
        //Given

        ItemDto itemDto = ItemDto.
                builder()
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(itemDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("L'artcle n'est pas valide");
        assertThat(ItemValidator.validate(itemDto)).isNotEqualTo(Collections.emptyList());
        then(itemRepository).should(never()).save(any());
    }

    @Test
    void givenItemId_whenFindItemById_thenShouldGetSuccess() {
        //Given
        Integer itemid = 28;
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryCode("Cat test")
                .designation("Designation test")
                .build();

        ItemDto itemDto = ItemDto.
                builder()
                .id(itemid)
                .itemCode(UUID.randomUUID().toString())
                .designation("Item 1")
                .unitPriceExclT(new BigDecimal(2000))
                .vat(new BigDecimal("0.1"))
                .unitPriceInclT(new BigDecimal(1800))
                .picture("")
                .companyId(3)
                .categoryDto(categoryDto)
                .build();
        given(itemRepository.findById(itemid)).willReturn(Optional.of(ItemDto.toEntity(itemDto)));

        //When
        underTest.findById(itemid);

        //Then
        then(itemRepository).should().findById(itemid);
    }

    @Test
    void givenNullForItemId_whenFindItemById_thenShouldntDoAnything() {
        //Given
        Integer itemOrderId = null;

        //when
        underTest.findById(itemOrderId);

        //then
        then(itemRepository).should(never()).findById(any());


    }

    @Test
    void givenItemId_whenFindItemById_thenShouldThrowEntityNotFoundException(){
        //Given
        Integer itemId = 28;
        given(itemRepository.findById(itemId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findById(itemId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun article avec l'id = " + itemId);
        then(itemRepository).should().findById(itemId);
    }

    @Test
    void givenItemCode_whenFindItemByCode_thenShouldGetSuccess() {
        //Given
        String itemCode = UUID.randomUUID().toString();
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryCode("Cat test")
                .designation("Designation test")
                .build();

        ItemDto itemDto = ItemDto.
                builder()
                .id(28)
                .itemCode(itemCode)
                .designation("Item 1")
                .unitPriceExclT(new BigDecimal(2000))
                .vat(new BigDecimal("0.1"))
                .unitPriceInclT(new BigDecimal(1800))
                .picture("")
                .companyId(3)
                .categoryDto(categoryDto)
                .build();
        given(itemRepository.findByItemCode(itemCode)).willReturn(Optional.of(ItemDto.toEntity(itemDto)));

        //When
        underTest.findByItemCode(itemCode);

        //Then
        then(itemRepository).should().findByItemCode(itemCode);
    }

    @Test
    void givenNullForItemCode_whenFindItemByCode_thenShouldntDoAnything() {
        //Given
        String itemCode = null;

        //when
        underTest.findByItemCode(itemCode);

        //then
        then(itemRepository).should(never()).findByItemCode(any());


    }

    @Test
    void givenItemCode_whenFindItemByCode_thenShouldThrowEntityNotFoundException(){
        //Given
        String itemCode = "IT-28";
        given(itemRepository.findByItemCode(itemCode)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findByItemCode(itemCode))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun article avec le code = " + itemCode);
        then(itemRepository).should().findByItemCode(itemCode);
    }

    @Test
    void whenFindAllItems_thenShouldGetSuccess() {
        //When
        underTest.findAll();

        //Then
        then(itemRepository).should().findAll();
    }

    @Test
    void givenItemId_whenDeleteItemById_thenShouldSaveSuccess() {
        //Given
        Integer itemId = 28;
        given(itemRepository.existsById(itemId)).willReturn(true);

        //When
        underTest.delete(itemId);

        //Then
        then(itemRepository).should().existsById(itemId);
        then(itemRepository).should().deleteById(itemId);
    }

    @Test
    void givenNullForItemId_whenDeleteItemById_thenShouldDoAnything(){
        //Given
        Integer itemId = null;

        //When
        underTest.delete(itemId);

        //Then
        then(itemRepository).should(never()).deleteById(any());
        then(itemRepository).should(never()).existsById(any());
    }

    @Test
    void givenItemId_whenDeleteItemById_thenShouldThrowEntityNotFoundException (){
        //Given
        Integer itemId = 28;
        given(itemRepository.existsById(itemId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(itemId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun article avec l'id = " + itemId);
        then(itemRepository).should().existsById(itemId);
        then(itemRepository).should(never()).deleteById(any());
    }
}