package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.AddressDto;
import com.yayaveli.inventorymanagement.dto.CompanyDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Company;
import com.yayaveli.inventorymanagement.repositories.CompanyRepository;
import com.yayaveli.inventorymanagement.validators.CompanyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class CompanyServiceImplTest {
    @Mock
    private CompanyRepository companyRepository;

    @Captor
    ArgumentCaptor<Company> companyArgumentCaptor;

    private CompanyServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new CompanyServiceImpl(companyRepository);
    }

    @Test
    void givenCompanyDto_whenAddCompany_thenShouldSaveSuccess() {
        //Given
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();
        CompanyDto companyDto = CompanyDto.builder()
                .name("DC")
                .description("DC Informatique")
                .taxCode("12000")
                .picture("")
                .email("dc@gmail.com")
                .phoneNumber("784671590")
                .address(addressDto)
                .webSite("")
                .build();
        System.out.println(companyDto);

        //When
        underTest.save(companyDto);

        //Then
        assertThat(CompanyValidator.validate(companyDto)).isEqualTo(Collections.emptyList());
        then(companyRepository).should().save(companyArgumentCaptor.capture());
        Company capturedCompany = companyArgumentCaptor.getValue();
        assertThat(capturedCompany).isEqualTo(CompanyDto.toEntity(companyDto));
        then(companyRepository).should().save(CompanyDto.toEntity(companyDto));

    }

    @Test
    void givenCompanyDto_whenAddCompany_thenShouldThrowInvalidEntityException(){
        //Given

        CompanyDto companyDto = CompanyDto.
                builder()
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(companyDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("L'entreprise n'est pas valide");
        assertThat(CompanyValidator.validate(companyDto)).isNotEqualTo(Collections.emptyList());
        then(companyRepository).should(never()).save(any());
    }

    @Test
    void givenCompanyId_whenFindCompanyById_thenShouldGetSuccess() {
        //Given
        Integer companyid = 28;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();
        CompanyDto companyDto = CompanyDto.builder()
                .id(companyid)
                .name("DC")
                .description("DC Informatique")
                .taxCode("12000")
                .picture("")
                .email("dc@gmail.com")
                .phoneNumber("784671590")
                .address(addressDto)
                .webSite("")
                .build();

        given(companyRepository.findById(companyid)).willReturn(Optional.of(CompanyDto.toEntity(companyDto)));

        //When
        underTest.findById(companyid);

        //Then
        then(companyRepository).should().findById(companyid);
    }

    @Test
    void givenNullForCompanyId_whenFindCompanyById_thenShouldntDoAnything() {
        //Given
        Integer companyOrderId = null;

        //when
        underTest.findById(companyOrderId);

        //then
        then(companyRepository).should(never()).findById(any());


    }

    @Test
    void givenCompanyId_whenFindCompanyById_thenShouldThrowEntityNotFoundException(){
        //Given
        Integer companyId = 28;
        given(companyRepository.findById(companyId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findById(companyId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune entreprise avec l'id = " + companyId);
        then(companyRepository).should().findById(companyId);
    }
    
    @Test
    void whenFindAllCompanies_thenShouldGetSuccess() {
        //When
        underTest.findAll();

        //Then
        then(companyRepository).should().findAll();
    }

    @Test
    void givenCompanyCode_whenFindCompanyByCode_thenShouldGetSuccess() {
        //Given
        String companyName = UUID.randomUUID().toString();
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();
        CompanyDto companyDto = CompanyDto.builder()
                .id(28)
                .name(companyName)
                .description("DC Informatique")
                .taxCode("12000")
                .picture("")
                .email("dc@gmail.com")
                .phoneNumber("784671590")
                .address(addressDto)
                .webSite("")
                .build();
        given(companyRepository.findByName(companyName)).willReturn(Optional.of(CompanyDto.toEntity(companyDto)));

        //When
        underTest.findByCompanyName(companyName);

        //Then
        then(companyRepository).should().findByName(companyName);
    }

    @Test
    void givenNullForCompanyCode_whenFindCompanyByCode_thenShouldntDoAnything() {
        //Given
        String companyName = null;

        //when
        underTest.findByCompanyName(companyName);

        //then
        then(companyRepository).should(never()).findByName(any());


    }

    @Test
    void givenCompanyCode_whenFindCompanyByCode_thenShouldThrowEntityNotFoundException(){
        //Given
        String companyName = "DC";
        given(companyRepository.findByName(companyName)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findByCompanyName(companyName))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune entreprise avec le nom = " + companyName);
        then(companyRepository).should().findByName(companyName);
    }


    @Test
    void givenCompanyId_whenDeleteCompanyById_thenShouldSaveSuccess() {
        //Given
        Integer companyId = 28;
        given(companyRepository.existsById(companyId)).willReturn(true);

        //When
        underTest.delete(companyId);

        //Then
        then(companyRepository).should().existsById(companyId);
        then(companyRepository).should().deleteById(companyId);
    }

    @Test
    void givenNullForCompanyId_whenDeleteCompanyById_thenShouldDoAnything(){
        //Given
        Integer companyId = null;

        //When
        underTest.delete(companyId);

        //Then
        then(companyRepository).should(never()).deleteById(any());
        then(companyRepository).should(never()).existsById(any());
    }

    @Test
    void givenCompanyId_whenDeleteCompanyById_thenShouldThrowEntityNotFoundException (){
        //Given
        Integer companyId = 28;
        given(companyRepository.existsById(companyId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(companyId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune entreprise avec l'id = " + companyId);
        then(companyRepository).should().existsById(companyId);
        then(companyRepository).should(never()).deleteById(any());
    }
}