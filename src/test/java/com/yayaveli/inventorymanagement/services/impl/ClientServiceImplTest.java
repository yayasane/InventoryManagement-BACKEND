package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.AddressDto;
import com.yayaveli.inventorymanagement.dto.ClientDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Client;
import com.yayaveli.inventorymanagement.repositories.ClientRepository;
import com.yayaveli.inventorymanagement.validators.ClientValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Captor
    ArgumentCaptor<Client> clientArgumentCaptor;

    private ClientServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ClientServiceImpl(clientRepository);
    }

    @Test
    void givenClientDto_whenAddClient_thenShouldSaveSuccess() {
        //Given
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

        //When
        underTest.save(clientDto);

        //Then
        assertThat(ClientValidator.validate(clientDto)).isEqualTo(Collections.emptyList());
        then(clientRepository).should().save(clientArgumentCaptor.capture());
        Client capturedClient = clientArgumentCaptor.getValue();
        assertThat(capturedClient).isEqualTo(ClientDto.toEntity(clientDto));
        then(clientRepository).should().save(ClientDto.toEntity(clientDto));

    }

    @Test
    void givenClientDto_whenAddClient_thenShouldThrowInvalidEntityException(){
        //Given

        ClientDto clientDto = ClientDto.
                builder()
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(clientDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("L'artcle n'est pas valide");
        assertThat(ClientValidator.validate(clientDto)).isNotEqualTo(Collections.emptyList());
        then(clientRepository).should(never()).save(any());
    }

    @Test
    void givenClientId_whenFindClientById_thenShouldGetSuccess() {
        //Given
        Integer clientid = 28;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ClientDto clientDto = ClientDto.
                builder()
                .id(clientid)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();
        given(clientRepository.findById(clientid)).willReturn(Optional.of(ClientDto.toEntity(clientDto)));

        //When
        underTest.findById(clientid);

        //Then
        then(clientRepository).should().findById(clientid);
    }

    @Test
    void givenNullForClientId_whenFindClientById_thenShouldntDoAnything() {
        //Given
        Integer clientOrderId = null;

        //when
        underTest.findById(clientOrderId);

        //then
        then(clientRepository).should(never()).findById(any());


    }

    @Test
    void givenClientId_whenFindClientById_thenShouldThrowEntityNotFoundException(){
        //Given
        Integer clientId = 28;
        given(clientRepository.findById(clientId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findById(clientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun article avec l'id = " + clientId);
        then(clientRepository).should().findById(clientId);
    }

    @Test
    void whenFindAllClients_thenShouldGetSuccess() {
        //When
        underTest.findAll();

        //Then
        then(clientRepository).should().findAll();
    }

    @Test
    void givenClientId_whenDeleteClientById_thenShouldSaveSuccess() {
        //Given
        Integer clientId = 28;
        given(clientRepository.existsById(clientId)).willReturn(true);

        //When
        underTest.delete(clientId);

        //Then
        then(clientRepository).should().existsById(clientId);
        then(clientRepository).should().deleteById(clientId);
    }

    @Test
    void givenNullForClientId_whenDeleteClientById_thenShouldDoAnything(){
        //Given
        Integer clientId = null;

        //When
        underTest.delete(clientId);

        //Then
        then(clientRepository).should(never()).deleteById(any());
        then(clientRepository).should(never()).existsById(any());
    }

    @Test
    void givenClientId_whenDeleteClientById_thenShouldThrowEntityNotFoundException (){
        //Given
        Integer clientId = 28;
        given(clientRepository.existsById(clientId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(clientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun client avec l'id = " + clientId);
        then(clientRepository).should().existsById(clientId);
        then(clientRepository).should(never()).deleteById(any());
    }
}