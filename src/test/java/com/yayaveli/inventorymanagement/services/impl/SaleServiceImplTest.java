package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.*;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.models.Sale;
import com.yayaveli.inventorymanagement.models.SaleLine;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
import com.yayaveli.inventorymanagement.repositories.SaleLineRepository;
import com.yayaveli.inventorymanagement.repositories.SaleRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {
    @Mock
    private SaleRepository saleRepository;
    @Mock
    private SaleLineRepository saleLineRepository;
    @Mock
    private ItemRepository itemRepository;

    @Captor
    private ArgumentCaptor<Sale> saleArgumentCaptor;

    private SaleServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new SaleServiceImpl(
                saleRepository,
                saleLineRepository,
                itemRepository
        );
    }

    @Test
    void givenSaleDtoWithoutSaleLines_whenAdded_thenShouldSaveSuccess() {
        //given

        SaleDto saleDto = SaleDto
                .builder()
                .id(null)
                .saleCode(UUID.randomUUID().toString())
                .companyId(1)
                .saleDate(Instant.now())
                .build();

        //when
        underTest.save(saleDto);

        //then
        then(saleRepository).should().save(saleArgumentCaptor.capture());
        Sale capturedSale = saleArgumentCaptor.getValue();
        assertThat(capturedSale).isEqualTo(SaleDto.toEntity(saleDto));
        System.out.println(capturedSale.getId());
//        assertThat(capturedSale.getId()).isNotNull();

        // Finally
        then(itemRepository).should(never()).save(any(Item.class));
        then(saleLineRepository).should(never()).save(any());
    }

    @Test
    void givenSaleDtoWithSaleLines_whenAdded_thenShouldSaveSuccess(){
        //given  SaleDto with ProviderDto and SaleLines all valid
        final int companyId = 1;
        

        SaleDto saleDto = SaleDto
                .builder()
                .saleCode(UUID.randomUUID().toString())
                .companyId(companyId)
                .saleDate(Instant.now())
                .build();
        List<SaleLineDto> saleLineDtos = new ArrayList<>();
        ItemDto itemDto1 = ItemDto.builder()
                .id(1)
                .itemCode(UUID.randomUUID().toString())
                .build();
        ItemDto itemDto2 = ItemDto.builder()
                .id(2)
                .itemCode(UUID.randomUUID().toString())
                .build();
        SaleLineDto saleLineDto1 = SaleLineDto
                .builder()
                .saleDto(saleDto)
                .companyId(companyId)
                .itemDto(itemDto1)
                .quantity(new BigDecimal(5))
                .unitPrice(new BigDecimal(2000))
                .build();
        SaleLineDto saleLineDto2 = SaleLineDto
                .builder()
                .saleDto(saleDto)
                .companyId(companyId)
                .itemDto(itemDto2)
                .quantity(new BigDecimal(2))
                .unitPrice(new BigDecimal(8000))
                .build();
        saleLineDtos.add(saleLineDto1);
        saleLineDtos.add(saleLineDto2);


        saleDto.setSaleLineDtos(saleLineDtos);
        System.out.println(saleDto.getSaleLineDtos().get(0).getItemDto());
        given(itemRepository.findById(saleDto.getSaleLineDtos().get(0).getItemDto().getId())).willReturn(Optional.of(ItemDto.toEntity(itemDto1)));
        given(itemRepository.findById(saleDto.getSaleLineDtos().get(1).getItemDto().getId())).willReturn(Optional.of(ItemDto.toEntity(itemDto2)));

        //when
        underTest.save(saleDto);

        //then
        ArgumentCaptor<Sale> saleArgumentCaptor = ArgumentCaptor.forClass(Sale.class);
        verify(saleRepository).save(saleArgumentCaptor.capture());
        Sale capturedSale = saleArgumentCaptor.getValue();
        assertThat(capturedSale).isEqualTo(SaleDto.toEntity(saleDto));
        System.out.println(capturedSale);

//        then(saleLineRepository).should().save(any(SaleLine.class));
//        then(itemRepository).should().findById(any());

//        ArgumentCaptor<SaleLine> saleLineArgumentCaptor1 = ArgumentCaptor.forClass(SaleLine.class);
//        verify(saleLineRepository).save(saleLineArgumentCaptor1.capture());
//        SaleLine capturedSale1 = saleLineArgumentCaptor1.getValue();
//        assertThat(capturedSale1).isEqualTo(SaleLineDto.toEntity(saleLineDto1));
//
//        ArgumentCaptor<SaleLine> saleLineArgumentCaptor2 = ArgumentCaptor.forClass(SaleLine.class);
//        verify(saleLineRepository).save(saleLineArgumentCaptor2.capture());
//        SaleLine capturedSale2 = saleLineArgumentCaptor2.getValue();
//        assertThat(capturedSale1).isEqualTo(SaleLineDto.toEntity(saleLineDto2));

    }

    @Test
    void givenInvalidSaleDto_whenAdded_thenShouldThrowInvalidEntityException(){
        //given

        //SaleDto without providerDto that's required for valid SaleDto
        SaleDto saleDto = SaleDto
                .builder()
                .build();
        //when
        //then
        assertThatThrownBy(()->underTest.save(saleDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("La vente n'est pas valide");
        // Finally
        verify(itemRepository, never()).findById(any());
        then(saleRepository).should(never()).save(any());
        then(saleLineRepository).should(never()).save(any());
    }

    @Test
    void givenSaleDtoWithSaleLines_whenAdded_thenShouldThrowInvalidEntityExceptionForItem(){
        //given  SaleDto with ProviderDto and SaleLines
        final int companyId = 1;


        SaleDto saleDto = SaleDto
                .builder()
                .saleCode(UUID.randomUUID().toString())
                .companyId(companyId)
                .saleDate(Instant.now())
                .build();
        List<SaleLineDto> saleLineDtos = new ArrayList<>();
        ItemDto itemDto1 = ItemDto.builder()
                .id(1)
                .itemCode(UUID.randomUUID().toString())
                .build();
        SaleLineDto saleLineDto1 = SaleLineDto
                .builder()
                .saleDto(saleDto)
                .companyId(companyId)
                .itemDto(itemDto1)
                .quantity(new BigDecimal(5))
                .unitPrice(new BigDecimal(2000))
                .build();
        SaleLineDto saleLineDto2 = SaleLineDto
                .builder()
                .saleDto(saleDto)
                .companyId(companyId)
                .quantity(new BigDecimal(2))
                .unitPrice(new BigDecimal(8000))
                .build();
        saleLineDtos.add(saleLineDto1);
        saleLineDtos.add(saleLineDto2);

        saleDto.setSaleLineDtos(saleLineDtos);

        given(itemRepository.findById(saleDto.getSaleLineDtos().get(0).getItemDto().getId())).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->underTest.save(saleDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("Un ou plusieur articles n'ont pas été trouvés dans la base de données")
        ;
        verify(itemRepository).findById(any());
        then(saleRepository).should(never()).save(SaleDto.toEntity(saleDto));
        then(saleLineRepository).should(never()).save(any(SaleLine.class));

    }

    @Test
    void givenSaleId_whenFindSaleById_thenShouldGetSuccess() {
        //given
        Integer saleId = 1;
        SaleDto saleDto = SaleDto
                .builder()
                .id(saleId)
                .saleDate(Instant.now())
                .companyId(2)
                .build();
        given(saleRepository.findById(saleId)).willReturn(Optional.of(SaleDto.toEntity(saleDto)));

        //when
        underTest.findById(saleId);

        //then
        then(saleRepository).should().findById(saleId);
    }

    @Test
    void givenSaleId_whenFindSaleById_thenShouldThrowEntityNotFoundException() {
        //given
        Integer saleId = 1;

        given(saleRepository.findById(saleId)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->underTest.findById(saleId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune vente avec l'id = " + saleId);
        then(saleRepository).should().findById(saleId);
    }

    @Test
    void givenNullForSaleId_whenFindByOrderId_thenShouldntDoAnything() {
        //Given
        Integer saleId = null;

        //when
        underTest.findById(saleId);

        //then
        then(saleRepository).should(never()).findById(any());


    }

    @Test
    void givenSaleCode_whenFindSaleByCode_thenShouldGetSuccess() {
        //given
        String saleCode = "ORD-04";
        SaleDto saleDto = SaleDto
                .builder()
                .saleCode(saleCode)
                .saleDate(Instant.now())
                .companyId(2)
                .build();
        given(saleRepository.findBySaleCode(saleCode)).willReturn(Optional.of(SaleDto.toEntity(saleDto)));

        //when
        underTest.findBySaleCode(saleCode);

        //then
        then(saleRepository).should().findBySaleCode(saleCode);
    }

    @Test
    void givenSaleCode_whenFindSaleByCode_thenShouldThrowEntityNotFoundException() {
        //given
        String saleCode = "ORD-04";

        given(saleRepository.findBySaleCode(saleCode)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(()->underTest.findBySaleCode(saleCode))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune vente avec le code = " + saleCode);
        then(saleRepository).should().findBySaleCode(saleCode);
    }

    @Test
    void givenNullForSaleCode_whenFindByOrderCode_thenShouldntDoAnything() {
        //Given
        String saleCode = null;

        //when
        underTest.findBySaleCode(saleCode);

        //then
        then(saleRepository).should(never()).findBySaleCode(any());


    }

    @Test
    void whenFindAll_thenShouldGetAllSuccess() {
        //when
        underTest.findAll();
        //then
        then(saleRepository).should().findAll();

    }

    @Test
    void givenSaleId_whenDeleted_thenShouldDeleteSuccess() {
        //Given
        Integer saleId = 3;
        given(saleRepository.existsById(saleId)).willReturn(true);

        //when
        underTest.delete(saleId);

        //then
        then(saleRepository).should().deleteById(saleId);


    }

    @Test
    void givenNullForSaleId_whenDeleted_thenShouldntDoAnything() {
        //Given
        Integer saleId = null;

        //when
        underTest.delete(saleId);

        //then
        then(saleRepository).should(never()).existsById(any());
        then(saleRepository).should(never()).deleteById(any());


    }

    @Test
    void givenSaleId_whenDeleted_thenShouldEntityNotFoundException(){
        //Given
        Integer saleId = 22;
        given(saleRepository.existsById(saleId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(saleId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucune vente avec l'id = " + saleId);
        then(saleRepository).should(never()).deleteById(any());
    }
}