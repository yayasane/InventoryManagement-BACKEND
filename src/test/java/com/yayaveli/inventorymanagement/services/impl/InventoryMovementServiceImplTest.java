package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.AddressDto;
import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.dto.InventoryMovementDto;
import com.yayaveli.inventorymanagement.dto.ItemDto;
import com.yayaveli.inventorymanagement.enums.InventoryMovementType;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.InventoryMovement;
import com.yayaveli.inventorymanagement.repositories.InventoryMovementRepository;
import com.yayaveli.inventorymanagement.validators.InventoryMovementValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
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
class InventoryMovementServiceImplTest {
    @Mock
    private InventoryMovementRepository inventoryMovementRepository;

    @Captor
    ArgumentCaptor<InventoryMovement> inventoryMovementArgumentCaptor;

    private InventoryMovementServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new InventoryMovementServiceImpl(inventoryMovementRepository);
    }

    @Test
    void givenInventoryMovementDto_whenAddInventoryMovement_thenShouldSaveSuccess() {
        //Given
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryCode("Cat test")
                .designation("Designation test")
                .build();
        ItemDto itemDto = ItemDto.
                builder()
                .id(28)
                .itemCode(UUID.randomUUID().toString())
                .designation("Item 1")
                .unitPriceExclT(new BigDecimal(2000))
                .vat(new BigDecimal("0.1"))
                .unitPriceInclT(new BigDecimal(1800))
                .picture("")
                .companyId(3)
                .categoryDto(categoryDto)
                .build();
        InventoryMovementDto inventoryMovementDto = InventoryMovementDto.builder()
                .inventoryMovementDate(Instant.now())
                .quantity(new BigDecimal(5))
                .companyId(3)
                .inventoryMovementType(InventoryMovementType.ENTREE)
                .item(itemDto)
                .build();
        System.out.println(inventoryMovementDto);

        //When
        underTest.save(inventoryMovementDto);

        //Then
        assertThat(InventoryMovementValidator.validate(inventoryMovementDto)).isEqualTo(Collections.emptyList());
        then(inventoryMovementRepository).should().save(inventoryMovementArgumentCaptor.capture());
        InventoryMovement capturedInventoryMovement = inventoryMovementArgumentCaptor.getValue();
        assertThat(capturedInventoryMovement).isEqualTo(InventoryMovementDto.toEntity(inventoryMovementDto));
        then(inventoryMovementRepository).should().save(InventoryMovementDto.toEntity(inventoryMovementDto));

    }

    @Test
    void givenInvalidInventoryMovementDto_whenAddInventoryMovement_thenShouldThrowInvalidEntityException(){
        //Given

        InventoryMovementDto inventoryMovementDto = InventoryMovementDto.
                builder()
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(inventoryMovementDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("Le mouvement de stock n'est pas valide");
        assertThat(InventoryMovementValidator.validate(inventoryMovementDto)).isNotEqualTo(Collections.emptyList());
        then(inventoryMovementRepository).should(never()).save(any());
    }

    @Test
    void givenInventoryMovementId_whenFindInventoryMovementById_thenShouldGetSuccess() {
        //Given
        Integer inventoryMovementid = 28;
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryCode("Cat test")
                .designation("Designation test")
                .build();
        ItemDto itemDto = ItemDto.
                builder()
                .id(28)
                .itemCode(UUID.randomUUID().toString())
                .designation("Item 1")
                .unitPriceExclT(new BigDecimal(2000))
                .vat(new BigDecimal("0.1"))
                .unitPriceInclT(new BigDecimal(1800))
                .picture("")
                .companyId(3)
                .categoryDto(categoryDto)
                .build();
        InventoryMovementDto inventoryMovementDto = InventoryMovementDto.builder()
                .id(inventoryMovementid)
                .inventoryMovementDate(Instant.now())
                .quantity(new BigDecimal(5))
                .companyId(3)
                .inventoryMovementType(InventoryMovementType.ENTREE)
                .item(itemDto)
                .build();

        given(inventoryMovementRepository.findById(inventoryMovementid)).willReturn(Optional.of(InventoryMovementDto.toEntity(inventoryMovementDto)));

        //When
        underTest.findById(inventoryMovementid);

        //Then
        then(inventoryMovementRepository).should().findById(inventoryMovementid);
    }

    @Test
    void givenNullForInventoryMovementId_whenFindInventoryMovementById_thenShouldntDoAnything() {
        //Given
        Integer inventoryMovementOrderId = null;

        //when
        underTest.findById(inventoryMovementOrderId);

        //then
        then(inventoryMovementRepository).should(never()).findById(any());


    }

    @Test
    void givenInventoryMovementId_whenFindInventoryMovementById_thenShouldThrowEntityNotFoundException(){
        //Given
        Integer inventoryMovementId = 28;
        given(inventoryMovementRepository.findById(inventoryMovementId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findById(inventoryMovementId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun mouvement de stock avec l'id = " + inventoryMovementId);
        then(inventoryMovementRepository).should().findById(inventoryMovementId);
    }

    @Test
    void whenFindAllInventoryMovements_thenShouldGetSuccess() {
        //When
        underTest.findAll();

        //Then
        then(inventoryMovementRepository).should().findAll();
    }

    

    @Test
    void givenInventoryMovementId_whenDeleteInventoryMovementById_thenShouldSaveSuccess() {
        //Given
        Integer inventoryMovementId = 28;
        given(inventoryMovementRepository.existsById(inventoryMovementId)).willReturn(true);

        //When
        underTest.delete(inventoryMovementId);

        //Then
        then(inventoryMovementRepository).should().existsById(inventoryMovementId);
        then(inventoryMovementRepository).should().deleteById(inventoryMovementId);
    }

    @Test
    void givenNullForInventoryMovementId_whenDeleteInventoryMovementById_thenShouldDoAnything(){
        //Given
        Integer inventoryMovementId = null;

        //When
        underTest.delete(inventoryMovementId);

        //Then
        then(inventoryMovementRepository).should(never()).deleteById(any());
        then(inventoryMovementRepository).should(never()).existsById(any());
    }

    @Test
    void givenInventoryMovementId_whenDeleteInventoryMovementById_thenShouldThrowEntityNotFoundException (){
        //Given
        Integer inventoryMovementId = 28;
        given(inventoryMovementRepository.existsById(inventoryMovementId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(inventoryMovementId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun mouvement de stock avec l'id = " + inventoryMovementId);
        then(inventoryMovementRepository).should().existsById(inventoryMovementId);
        then(inventoryMovementRepository).should(never()).deleteById(any());
    }
}