package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.*;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.ClientOrder;
import com.yayaveli.inventorymanagement.models.ClientOrderLine;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.repositories.ClientOrderLineRepository;
import com.yayaveli.inventorymanagement.repositories.ClientOrderRepository;
import com.yayaveli.inventorymanagement.repositories.ClientRepository;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
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
class ClientOrderServiceImplTest {
    @Mock
    private ClientOrderServiceImpl undertest;
    @Mock
    private ClientOrderRepository clientOrderRepository;
    @Mock
    private ClientOrderLineRepository clientOrderLineRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ItemRepository itemRepository;
    @Captor
    private ArgumentCaptor<ClientOrder> clientOrderArgumentCaptor;

    @BeforeEach
    void setUp() {
        undertest = new ClientOrderServiceImpl(
                clientOrderRepository,
                clientOrderLineRepository,
                clientRepository,
                itemRepository
        );
    }

    @Test
    void givenClientOrderDtoWithoutClientOrderLines_whenAdded_thenShouldSaveSuccess() {
        //given
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ClientDto clientDto = ClientDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ClientOrderDto clientOrderDto = ClientOrderDto
                .builder()
                .id(null)
                .orderCode(UUID.randomUUID().toString())
                .companyId(1)
                .orderDate(Instant.now())
                .clientDto(clientDto)
                .build();
        given(clientRepository.findById(clientOrderDto.getClientDto().getId())).willReturn(Optional.of(ClientDto.toEntity(clientDto)));

        //when
        undertest.save(clientOrderDto);

        //then
        then(clientOrderRepository).should().save(clientOrderArgumentCaptor.capture());
        ClientOrder capturedClientOrder = clientOrderArgumentCaptor.getValue();
        assertThat(capturedClientOrder).isEqualTo(ClientOrderDto.toEntity(clientOrderDto));
        System.out.println(capturedClientOrder.getId());
//        assertThat(capturedClientOrder.getId()).isNotNull();

        // Finally
        then(clientRepository).should().findById(clientOrderDto.getClientDto().getId());
        then(itemRepository).should(never()).save(any(Item.class));
        then(clientOrderLineRepository).should(never()).save(any());
    }

    @Test
    void givenClientOrderDtoWithClientOrderLines_whenAdded_thenShouldSaveSuccess(){
        //given  ClientOrderDto with ClientDto and ClientOrderLines all valid
        final int companyId = 1;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ClientDto clientDto = ClientDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ClientOrderDto clientOrderDto = ClientOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(companyId)
                .orderDate(Instant.now())
                .clientDto(clientDto)
                .build();
        List<ClientOrderLineDto> clientOrderLineDtos = new ArrayList<>();
        ItemDto itemDto1 = ItemDto.builder()
                .id(1)
                .itemCode(UUID.randomUUID().toString())
                .build();
        ItemDto itemDto2 = ItemDto.builder()
                .id(2)
                .itemCode(UUID.randomUUID().toString())
                .build();
        ClientOrderLineDto clientOrderLineDto1 = ClientOrderLineDto
                .builder()
                .clientOrderDto(clientOrderDto)
                .companyId(companyId)
                .itemDto(itemDto1)
                .quantity(new BigDecimal(5))
                .unitPrice(new BigDecimal(2000))
                .build();
        ClientOrderLineDto clientOrderLineDto2 = ClientOrderLineDto
                .builder()
                .clientOrderDto(clientOrderDto)
                .companyId(companyId)
                .itemDto(itemDto2)
                .quantity(new BigDecimal(2))
                .unitPrice(new BigDecimal(8000))
                .build();
        clientOrderLineDtos.add(clientOrderLineDto1);
        clientOrderLineDtos.add(clientOrderLineDto2);


        clientOrderDto.setClientOrderLines(clientOrderLineDtos);
        System.out.println(clientOrderDto.getClientOrderLines().get(0).getItemDto());
        given(clientRepository.findById(clientOrderDto.getClientDto().getId())).willReturn(Optional.of(ClientDto.toEntity(clientDto)));
        given(itemRepository.findById(clientOrderDto.getClientOrderLines().get(0).getItemDto().getId())).willReturn(Optional.of(ItemDto.toEntity(itemDto1)));
        given(itemRepository.findById(clientOrderDto.getClientOrderLines().get(1).getItemDto().getId())).willReturn(Optional.of(ItemDto.toEntity(itemDto2)));

        //when
        undertest.save(clientOrderDto);

        //then
        ArgumentCaptor<ClientOrder> clientOrderArgumentCaptor = ArgumentCaptor.forClass(ClientOrder.class);
        verify(clientOrderRepository).save(clientOrderArgumentCaptor.capture());
        ClientOrder capturedClientOrder = clientOrderArgumentCaptor.getValue();
        assertThat(capturedClientOrder).isEqualTo(ClientOrderDto.toEntity(clientOrderDto));
        System.out.println(capturedClientOrder);

//        then(clientOrderLineRepository).should().save(any(ClientOrderLine.class));
        then(clientRepository).should().findById(any());
//        then(itemRepository).should().findById(any());

//        ArgumentCaptor<ClientOrderLine> clientOrderLineArgumentCaptor1 = ArgumentCaptor.forClass(ClientOrderLine.class);
//        verify(clientOrderLineRepository).save(clientOrderLineArgumentCaptor1.capture());
//        ClientOrderLine capturedClientOrder1 = clientOrderLineArgumentCaptor1.getValue();
//        assertThat(capturedClientOrder1).isEqualTo(ClientOrderLineDto.toEntity(clientOrderLineDto1));
//
//        ArgumentCaptor<ClientOrderLine> clientOrderLineArgumentCaptor2 = ArgumentCaptor.forClass(ClientOrderLine.class);
//        verify(clientOrderLineRepository).save(clientOrderLineArgumentCaptor2.capture());
//        ClientOrderLine capturedClientOrder2 = clientOrderLineArgumentCaptor2.getValue();
//        assertThat(capturedClientOrder1).isEqualTo(ClientOrderLineDto.toEntity(clientOrderLineDto2));

    }

    @Test
    void givenClientOrderDtoWithoutClient_whenAdded_thenShouldThrowInvalidEntityException(){
        //given

        //ClientOrderDto without clientDto that's required for valid ClientOrderDto
        ClientOrderDto clientOrderDto = ClientOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(1)
                .orderDate(Instant.now())
                .build();
        //when
        //then
        assertThatThrownBy(()->undertest.save(clientOrderDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("La commande client n'est pas valide");
        // Finally
        then(clientRepository).should(never()).findById(any());
        verify(itemRepository, never()).findById(any());
        then(clientOrderRepository).should(never()).save(any());
        then(clientOrderLineRepository).should(never()).save(any());
    }

    @Test
    void givenClientOrderDto_whenAdded_thenShouldThrowEntityNotFoundExceptionForClient(){
        //given
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ClientDto clientDto = ClientDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ClientOrderDto clientOrderDto = ClientOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(1)
                .orderDate(Instant.now())
                .clientDto(clientDto)
                .build();
        // ... No client found with id passed
        given(clientRepository.findById(clientOrderDto.getClientDto().getId())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->undertest.save(clientOrderDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun client avec l'ID " + clientOrderDto.getClientDto().getId() + " n'a été trouvé dans la BD");
        then(clientRepository).should().findById(any());
        verify(clientOrderRepository, never()).save(any());
        then(clientOrderLineRepository).should(never()).save(any(ClientOrderLine.class));
        verify(itemRepository, never()).findById(any());

    }

    @Test
    void givenClientOrderDtoWithClientOrderLines_whenAdded_thenShouldThrowInvalidEntityExceptionForItem(){
        //given  ClientOrderDto with ClientDto and ClientOrderLines
        final int companyId = 1;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ClientDto clientDto = ClientDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        ClientOrderDto clientOrderDto = ClientOrderDto
                .builder()
                .orderCode(UUID.randomUUID().toString())
                .companyId(companyId)
                .orderDate(Instant.now())
                .clientDto(clientDto)
                .build();
        List<ClientOrderLineDto> clientOrderLineDtos = new ArrayList<>();
        ItemDto itemDto1 = ItemDto.builder()
                .id(1)
                .itemCode(UUID.randomUUID().toString())
                .build();
        ClientOrderLineDto clientOrderLineDto1 = ClientOrderLineDto
                .builder()
                .clientOrderDto(clientOrderDto)
                .companyId(companyId)
                .itemDto(itemDto1)
                .quantity(new BigDecimal(5))
                .unitPrice(new BigDecimal(2000))
                .build();
        ClientOrderLineDto clientOrderLineDto2 = ClientOrderLineDto
                .builder()
                .clientOrderDto(clientOrderDto)
                .companyId(companyId)
                .quantity(new BigDecimal(2))
                .unitPrice(new BigDecimal(8000))
                .build();
        clientOrderLineDtos.add(clientOrderLineDto1);
        clientOrderLineDtos.add(clientOrderLineDto2);

        clientOrderDto.setClientOrderLines(clientOrderLineDtos);

        given(clientRepository.findById(clientOrderDto.getClientDto().getId())).willReturn(Optional.of(ClientDto.toEntity(clientDto)));
        given(itemRepository.findById(clientOrderDto.getClientOrderLines().get(0).getItemDto().getId())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->undertest.save(clientOrderDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("Article n'existe dans la base de données")
        ;
        then(clientRepository).should().findById(any());
        verify(itemRepository).findById(any());
        then(clientOrderRepository).should(never()).save(ClientOrderDto.toEntity(clientOrderDto));
        then(clientOrderLineRepository).should(never()).save(any(ClientOrderLine.class));

    }

    @Test
    void givenClientOrderId_whenFindClientOrderById_thenShouldGetSuccess() {
        //given
        Integer clientOrderId = 1;
        ClientOrderDto clientOrderDto = ClientOrderDto
                .builder()
                .id(clientOrderId)
                .orderDate(Instant.now())
                .companyId(2)
                .build();
        given(clientOrderRepository.findById(clientOrderId)).willReturn(Optional.of(ClientOrderDto.toEntity(clientOrderDto)));

        //when
        undertest.findById(clientOrderId);

        //then
        then(clientOrderRepository).should().findById(clientOrderId);
    }

    @Test
    void givenClientOrderId_whenFindClientOrderById_thenShouldThrowEntityNotFoundException() {
        //given
        Integer clientOrderId = 1;

        given(clientOrderRepository.findById(clientOrderId)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->undertest.findById(clientOrderId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune commande avec l'id = " + clientOrderId);
        then(clientOrderRepository).should().findById(clientOrderId);
    }

    @Test
    void givenNullForClientOrderId_whenFindByOrderId_thenShouldntDoAnything() {
        //Given
        Integer clientOrderId = null;

        //when
        undertest.findById(clientOrderId);

        //then
        then(clientOrderRepository).should(never()).findById(any());


    }

@Test
    void givenClientOrderCode_whenFindClientOrderByCode_thenShouldGetSuccess() {
        //given
    String clientOrderCode = "ORD-04";
        ClientOrderDto clientOrderDto = ClientOrderDto
                .builder()
                .orderCode(clientOrderCode)
                .orderDate(Instant.now())
                .companyId(2)
                .build();
        given(clientOrderRepository.findByOrderCode(clientOrderCode)).willReturn(Optional.of(ClientOrderDto.toEntity(clientOrderDto)));

        //when
        undertest.findByOrderCode(clientOrderCode);

        //then
        then(clientOrderRepository).should().findByOrderCode(clientOrderCode);
    }

    @Test
    void givenClientOrderCode_whenFindClientOrderByCode_thenShouldThrowEntityNotFoundException() {
        //given
        String clientOrderCode = "ORD-04";

        given(clientOrderRepository.findByOrderCode(clientOrderCode)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->undertest.findByOrderCode(clientOrderCode))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune commande avec le code = " + clientOrderCode);
        then(clientOrderRepository).should().findByOrderCode(clientOrderCode);
    }

    @Test
    void givenNullForClientOrderCode_whenFindByOrderCode_thenShouldntDoAnything() {
        //Given
        String clientOrderCode = null;

        //when
        undertest.findByOrderCode(clientOrderCode);

        //then
        then(clientOrderRepository).should(never()).findByOrderCode(any());


    }

    @Test
    void whenFindAll_thenShouldGetAllSuccess() {
        //when
        undertest.findAll();
        //then
        then(clientOrderRepository).should().findAll();

    }

    @Test
    void givenClientOrderId_whenDeleted_thenShouldDeleteSuccess() {
        //Given
        Integer clientOrderId = 3;
        given(clientOrderRepository.existsById(clientOrderId)).willReturn(true);

        //when
        undertest.delete(clientOrderId);

        //then
        then(clientOrderRepository).should().deleteById(clientOrderId);


    }

    @Test
    void givenNullForClientOrderId_whenDeleted_thenShouldntDoAnything() {
        //Given
        Integer clientOrderId = null;

        //when
        undertest.delete(clientOrderId);

        //then
        then(clientOrderRepository).should(never()).existsById(any());
        then(clientOrderRepository).should(never()).deleteById(any());


    }

    @Test
    void givenClientOrderId_whenDeleted_thenShouldEntityNotFoundException(){
        //Given
        Integer clientOrderId = 22;
        given(clientOrderRepository.existsById(clientOrderId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->undertest.delete(clientOrderId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune commande avec l'id = " + clientOrderId);
        then(clientOrderRepository).should(never()).deleteById(any());
    }
}