package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.AddressDto;
import com.yayaveli.inventorymanagement.dto.ProviderDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Provider;
import com.yayaveli.inventorymanagement.repositories.ProviderRepository;
import com.yayaveli.inventorymanagement.validators.ProviderValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {

    @Mock
    private ProviderRepository providerRepository;

    @Captor
    ArgumentCaptor<Provider> providerArgumentCaptor;

    private ProviderServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProviderServiceImpl(providerRepository);
    }

    @Test
    void givenProviderDto_whenAddProvider_thenShouldSaveSuccess() {
        //Given
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

        //When
        underTest.save(providerDto);

        //Then
        assertThat(ProviderValidator.validate(providerDto)).isEqualTo(Collections.emptyList());
        then(providerRepository).should().save(providerArgumentCaptor.capture());
        Provider capturedProvider = providerArgumentCaptor.getValue();
        assertThat(capturedProvider).isEqualTo(ProviderDto.toEntity(providerDto));
        then(providerRepository).should().save(ProviderDto.toEntity(providerDto));

    }

    @Test
    void givenProviderDto_whenAddProvider_thenShouldThrowInvalidEntityException(){
        //Given

        ProviderDto providerDto = ProviderDto.
                builder()
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(providerDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("Le fournisseur n'est pas valide");
        assertThat(ProviderValidator.validate(providerDto)).isNotEqualTo(Collections.emptyList());
        then(providerRepository).should(never()).save(any());
    }

    @Test
    void givenProviderId_whenFindProviderById_thenShouldGetSuccess() {
        //Given
        Integer providerid = 28;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        ProviderDto providerDto = ProviderDto.
                builder()
                .id(providerid)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();
        given(providerRepository.findById(providerid)).willReturn(Optional.of(ProviderDto.toEntity(providerDto)));

        //When
        underTest.findById(providerid);

        //Then
        then(providerRepository).should().findById(providerid);
    }

    @Test
    void givenNullForProviderId_whenFindProviderById_thenShouldntDoAnything() {
        //Given
        Integer providerOrderId = null;

        //when
        underTest.findById(providerOrderId);

        //then
        then(providerRepository).should(never()).findById(any());


    }

    @Test
    void givenProviderId_whenFindProviderById_thenShouldThrowEntityNotFoundException(){
        //Given
        Integer providerId = 28;
        given(providerRepository.findById(providerId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findById(providerId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun fournisseur avec l'id = " + providerId);
        then(providerRepository).should().findById(providerId);
    }

    @Test
    void whenFindAllProviders_thenShouldGetSuccess() {
        //When
        underTest.findAll();

        //Then
        then(providerRepository).should().findAll();
    }

    @Test
    void givenProviderId_whenDeleteProviderById_thenShouldSaveSuccess() {
        //Given
        Integer providerId = 28;
        given(providerRepository.existsById(providerId)).willReturn(true);

        //When
        underTest.delete(providerId);

        //Then
        then(providerRepository).should().existsById(providerId);
        then(providerRepository).should().deleteById(providerId);
    }

    @Test
    void givenNullForProviderId_whenDeleteProviderById_thenShouldDoAnything(){
        //Given
        Integer providerId = null;

        //When
        underTest.delete(providerId);

        //Then
        then(providerRepository).should(never()).deleteById(any());
        then(providerRepository).should(never()).existsById(any());
    }

    @Test
    void givenProviderId_whenDeleteProviderById_thenShouldThrowEntityNotFoundException (){
        //Given
        Integer providerId = 28;
        given(providerRepository.existsById(providerId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(providerId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun fournisseur avec l'id = " + providerId);
        then(providerRepository).should().existsById(providerId);
        then(providerRepository).should(never()).deleteById(any());
    }
}