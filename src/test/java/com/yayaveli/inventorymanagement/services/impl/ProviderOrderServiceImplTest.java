package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.*;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.models.ProviderOrder;
import com.yayaveli.inventorymanagement.models.ProviderOrderLine;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
import com.yayaveli.inventorymanagement.repositories.ProviderOrderLineRepository;
import com.yayaveli.inventorymanagement.repositories.ProviderOrderRepository;
import com.yayaveli.inventorymanagement.repositories.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProviderOrderServiceImplTest {
    @Mock
    private ProviderOrderRepository providerOrderRepository;
    @Mock
    private ProviderOrderLineRepository providerOrderLineRepository;
    @Mock
    private ProviderRepository providerRepository;
    @Mock
    private ItemRepository itemRepository;
    
    @Captor
    private ArgumentCaptor<ProviderOrder> providerOrderArgumentCaptor;

    private ProviderOrderServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProviderOrderServiceImpl(
                providerOrderRepository,
                providerOrderLineRepository,
                providerRepository,
                itemRepository
        );
    }

    @Test
    void givenProviderOrderDtoWithoutProviderOrderLines_whenAdded_thenShouldSaveSuccess() {
        //given
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ProviderDto providerDto = ProviderDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ProviderOrderDto providerOrderDto = ProviderOrderDto
                .builder()
                .id(null)
                .orderCode(UUID.randomUUID().toString())
                .companyId(1)
                .orderDate(Instant.now())
                .providerDto(providerDto)
                .build();
        given(providerRepository.findById(providerOrderDto.getProviderDto().getId())).willReturn(Optional.of(ProviderDto.toEntity(providerDto)));

        //when
        underTest.save(providerOrderDto);

        //then
        then(providerOrderRepository).should().save(providerOrderArgumentCaptor.capture());
        ProviderOrder capturedProviderOrder = providerOrderArgumentCaptor.getValue();
        assertThat(capturedProviderOrder).isEqualTo(ProviderOrderDto.toEntity(providerOrderDto));
        System.out.println(capturedProviderOrder.getId());
//        assertThat(capturedProviderOrder.getId()).isNotNull();

        // Finally
        then(providerRepository).should().findById(providerOrderDto.getProviderDto().getId());
        then(itemRepository).should(never()).save(any(Item.class));
        then(providerOrderLineRepository).should(never()).save(any());
    }

    @Test
    void givenProviderOrderDtoWithProviderOrderLines_whenAdded_thenShouldSaveSuccess(){
        //given  ProviderOrderDto with ProviderDto and ProviderOrderLines all valid
        final int companyId = 1;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ProviderDto providerDto = ProviderDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ProviderOrderDto providerOrderDto = ProviderOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(companyId)
                .orderDate(Instant.now())
                .providerDto(providerDto)
                .build();
        List<ProviderOrderLineDto> providerOrderLineDtos = new ArrayList<>();
        ItemDto itemDto1 = ItemDto.builder()
                .id(1)
                .itemCode(UUID.randomUUID().toString())
                .build();
        ItemDto itemDto2 = ItemDto.builder()
                .id(2)
                .itemCode(UUID.randomUUID().toString())
                .build();
        ProviderOrderLineDto providerOrderLineDto1 = ProviderOrderLineDto
                .builder()
                .providerOrderDto(providerOrderDto)
                .companyId(companyId)
                .itemDto(itemDto1)
                .quantity(new BigDecimal(5))
                .unitPrice(new BigDecimal(2000))
                .build();
        ProviderOrderLineDto providerOrderLineDto2 = ProviderOrderLineDto
                .builder()
                .providerOrderDto(providerOrderDto)
                .companyId(companyId)
                .itemDto(itemDto2)
                .quantity(new BigDecimal(2))
                .unitPrice(new BigDecimal(8000))
                .build();
        providerOrderLineDtos.add(providerOrderLineDto1);
        providerOrderLineDtos.add(providerOrderLineDto2);


        providerOrderDto.setProviderOrderLines(providerOrderLineDtos);
        System.out.println(providerOrderDto.getProviderOrderLines().get(0).getItemDto());
        given(providerRepository.findById(providerOrderDto.getProviderDto().getId())).willReturn(Optional.of(ProviderDto.toEntity(providerDto)));
        given(itemRepository.findById(providerOrderDto.getProviderOrderLines().get(0).getItemDto().getId())).willReturn(Optional.of(ItemDto.toEntity(itemDto1)));
        given(itemRepository.findById(providerOrderDto.getProviderOrderLines().get(1).getItemDto().getId())).willReturn(Optional.of(ItemDto.toEntity(itemDto2)));

        //when
        underTest.save(providerOrderDto);

        //then
        ArgumentCaptor<ProviderOrder> providerOrderArgumentCaptor = ArgumentCaptor.forClass(ProviderOrder.class);
        verify(providerOrderRepository).save(providerOrderArgumentCaptor.capture());
        ProviderOrder capturedProviderOrder = providerOrderArgumentCaptor.getValue();
        assertThat(capturedProviderOrder).isEqualTo(ProviderOrderDto.toEntity(providerOrderDto));
        System.out.println(capturedProviderOrder);

//        then(providerOrderLineRepository).should().save(any(ProviderOrderLine.class));
        then(providerRepository).should().findById(any());
//        then(itemRepository).should().findById(any());

//        ArgumentCaptor<ProviderOrderLine> providerOrderLineArgumentCaptor1 = ArgumentCaptor.forClass(ProviderOrderLine.class);
//        verify(providerOrderLineRepository).save(providerOrderLineArgumentCaptor1.capture());
//        ProviderOrderLine capturedProviderOrder1 = providerOrderLineArgumentCaptor1.getValue();
//        assertThat(capturedProviderOrder1).isEqualTo(ProviderOrderLineDto.toEntity(providerOrderLineDto1));
//
//        ArgumentCaptor<ProviderOrderLine> providerOrderLineArgumentCaptor2 = ArgumentCaptor.forClass(ProviderOrderLine.class);
//        verify(providerOrderLineRepository).save(providerOrderLineArgumentCaptor2.capture());
//        ProviderOrderLine capturedProviderOrder2 = providerOrderLineArgumentCaptor2.getValue();
//        assertThat(capturedProviderOrder1).isEqualTo(ProviderOrderLineDto.toEntity(providerOrderLineDto2));

    }

    @Test
    void givenProviderOrderDtoWithoutProvider_whenAdded_thenShouldThrowInvalidEntityException(){
        //given

        //ProviderOrderDto without providerDto that's required for valid ProviderOrderDto
        ProviderOrderDto providerOrderDto = ProviderOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(1)
                .orderDate(Instant.now())
                .build();
        //when
        //then
        assertThatThrownBy(()->underTest.save(providerOrderDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("La commande fournisseur n'est pas valide");
        // Finally
        then(providerRepository).should(never()).findById(any());
        verify(itemRepository, never()).findById(any());
        then(providerOrderRepository).should(never()).save(any());
        then(providerOrderLineRepository).should(never()).save(any());
    }

    @Test
    void givenProviderOrderDto_whenAdded_thenShouldThrowEntityNotFoundExceptionForProvider(){
        //given
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ProviderDto providerDto = ProviderDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ProviderOrderDto providerOrderDto = ProviderOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(1)
                .orderDate(Instant.now())
                .providerDto(providerDto)
                .build();
        // ... No provider found with id passed
        given(providerRepository.findById(providerOrderDto.getProviderDto().getId())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->underTest.save(providerOrderDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun fournisseur avec l'ID " + providerOrderDto.getProviderDto().getId() + " n'a été trouvé dans la BD");
        then(providerRepository).should().findById(any());
        verify(providerOrderRepository, never()).save(any());
        then(providerOrderLineRepository).should(never()).save(any(ProviderOrderLine.class));
        verify(itemRepository, never()).findById(any());

    }

    @Test
    void givenProviderOrderDtoWithProviderOrderLines_whenAdded_thenShouldThrowInvalidEntityExceptionForItem(){
        //given  ProviderOrderDto with ProviderDto and ProviderOrderLines
        final int companyId = 1;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ProviderDto providerDto = ProviderDto.
                builder()
            .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ProviderOrderDto providerOrderDto = ProviderOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(companyId)
                .orderDate(Instant.now())
                .providerDto(providerDto)
                .build();
        List<ProviderOrderLineDto> providerOrderLineDtos = new ArrayList<>();
        ItemDto itemDto1 = ItemDto.builder()
                .id(1)
                .itemCode(UUID.randomUUID().toString())
                .build();
        ProviderOrderLineDto providerOrderLineDto1 = ProviderOrderLineDto
                .builder()
                .providerOrderDto(providerOrderDto)
                .companyId(companyId)
                .itemDto(itemDto1)
                .quantity(new BigDecimal(5))
                .unitPrice(new BigDecimal(2000))
                .build();
        ProviderOrderLineDto providerOrderLineDto2 = ProviderOrderLineDto
                .builder()
                .providerOrderDto(providerOrderDto)
                .companyId(companyId)
                .quantity(new BigDecimal(2))
                .unitPrice(new BigDecimal(8000))
                .build();
        providerOrderLineDtos.add(providerOrderLineDto1);
        providerOrderLineDtos.add(providerOrderLineDto2);

        providerOrderDto.setProviderOrderLines(providerOrderLineDtos);

        given(providerRepository.findById(providerOrderDto.getProviderDto().getId())).willReturn(Optional.of(ProviderDto.toEntity(providerDto)));
        given(itemRepository.findById(providerOrderDto.getProviderOrderLines().get(0).getItemDto().getId())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->underTest.save(providerOrderDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("Article n'existe dans la base de données")
        ;
        then(providerRepository).should().findById(any());
        verify(itemRepository).findById(any());
        then(providerOrderRepository).should(never()).save(ProviderOrderDto.toEntity(providerOrderDto));
        then(providerOrderLineRepository).should(never()).save(any(ProviderOrderLine.class));

    }

    @Test
    void givenProviderOrderId_whenFindProviderOrderById_thenShouldGetSuccess() {
        //given
        Integer providerOrderId = 1;
        ProviderOrderDto providerOrderDto = ProviderOrderDto
                .builder()
                .id(providerOrderId)
                .orderDate(Instant.now())
                .companyId(2)
                .build();
        given(providerOrderRepository.findById(providerOrderId)).willReturn(Optional.of(ProviderOrderDto.toEntity(providerOrderDto)));

        //when
        underTest.findById(providerOrderId);

        //then
        then(providerOrderRepository).should().findById(providerOrderId);
    }

    @Test
    void givenProviderOrderId_whenFindProviderOrderById_thenShouldThrowEntityNotFoundException() {
        //given
        Integer providerOrderId = 1;

        given(providerOrderRepository.findById(providerOrderId)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->underTest.findById(providerOrderId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune commande avec l'id = " + providerOrderId);
        then(providerOrderRepository).should().findById(providerOrderId);
    }

    @Test
    void givenNullForProviderOrderId_whenFindByOrderId_thenShouldntDoAnything() {
        //Given
        Integer providerOrderId = null;

        //when
        underTest.findById(providerOrderId);

        //then
        then(providerOrderRepository).should(never()).findById(any());


    }

    @Test
    void givenProviderOrderCode_whenFindProviderOrderByCode_thenShouldGetSuccess() {
        //given
        String providerOrderCode = "ORD-04";
        ProviderOrderDto providerOrderDto = ProviderOrderDto
                .builder()
                .orderCode(providerOrderCode)
                .orderDate(Instant.now())
                .companyId(2)
                .build();
        given(providerOrderRepository.findByOrderCode(providerOrderCode)).willReturn(Optional.of(ProviderOrderDto.toEntity(providerOrderDto)));

        //when
        underTest.findByOrderCode(providerOrderCode);

        //then
        then(providerOrderRepository).should().findByOrderCode(providerOrderCode);
    }

    @Test
    void givenProviderOrderCode_whenFindProviderOrderByCode_thenShouldThrowEntityNotFoundException() {
        //given
        String providerOrderCode = "ORD-04";

        given(providerOrderRepository.findByOrderCode(providerOrderCode)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->underTest.findByOrderCode(providerOrderCode))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune commande avec le code = " + providerOrderCode);
        then(providerOrderRepository).should().findByOrderCode(providerOrderCode);
    }

    @Test
    void givenNullForProviderOrderCode_whenFindByOrderCode_thenShouldntDoAnything() {
        //Given
        String providerOrderCode = null;

        //when
        underTest.findByOrderCode(providerOrderCode);

        //then
        then(providerOrderRepository).should(never()).findByOrderCode(any());


    }

    @Test
    void whenFindAll_thenShouldGetAllSuccess() {
        //when
        underTest.findAll();
        //then
        then(providerOrderRepository).should().findAll();

    }

    @Test
    void givenProviderOrderId_whenDeleted_thenShouldDeleteSuccess() {
        //Given
        Integer providerOrderId = 3;
        given(providerOrderRepository.existsById(providerOrderId)).willReturn(true);

        //when
        underTest.delete(providerOrderId);

        //then
        then(providerOrderRepository).should().deleteById(providerOrderId);


    }

    @Test
    void givenNullForProviderOrderId_whenDeleted_thenShouldntDoAnything() {
        //Given
        Integer providerOrderId = null;

        //when
        underTest.delete(providerOrderId);

        //then
        then(providerOrderRepository).should(never()).existsById(any());
        then(providerOrderRepository).should(never()).deleteById(any());


    }

    @Test
    void givenProviderOrderId_whenDeleted_thenShouldEntityNotFoundException(){
        //Given
        Integer providerOrderId = 22;
        given(providerOrderRepository.existsById(providerOrderId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(providerOrderId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune commande avec l'id = " + providerOrderId);
        then(providerOrderRepository).should(never()).deleteById(any());
    }
}