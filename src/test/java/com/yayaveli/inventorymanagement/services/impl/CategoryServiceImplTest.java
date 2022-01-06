package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.CategoryDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Category;
import com.yayaveli.inventorymanagement.repositories.CategoryRepository;
import com.yayaveli.inventorymanagement.validators.CategoryValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    
        @Mock
        private CategoryRepository categoryRepository;

        @Captor
        ArgumentCaptor<Category> categoryArgumentCaptor;

        private CategoryServiceImpl underTest;

        @BeforeEach
        void setUp() {
            underTest = new CategoryServiceImpl(categoryRepository);
        }

        @Test
        void givenCategoryDto_whenAddCategory_thenShouldSaveSuccess() {
            //Given
            CategoryDto categoryDto = CategoryDto.builder()
                    .categoryCode("Cat test")
                    .designation("Designation test")
                    .build();

            //When
            underTest.save(categoryDto);

            //Then
            assertThat(CategoryValidator.validate(categoryDto)).isEqualTo(Collections.emptyList());
            then(categoryRepository).should().save(categoryArgumentCaptor.capture());
            Category capturedCategory = categoryArgumentCaptor.getValue();
            assertThat(capturedCategory).isEqualTo(CategoryDto.toEntity(categoryDto));
            then(categoryRepository).should().save(CategoryDto.toEntity(categoryDto));

        }

        @Test
        void givenCategoryDto_whenAddCategory_thenShouldThrowInvalidEntityException(){
            //Given

            CategoryDto categoryDto = CategoryDto.
                    builder()
                    .build();

            //When
            //Then
            assertThatThrownBy(()->underTest.save(categoryDto))
                    .isInstanceOf(InvalidEntityException.class)
                    .hasMessage("La catégorie n'est pas valide");
            assertThat(CategoryValidator.validate(categoryDto)).isNotEqualTo(Collections.emptyList());
            then(categoryRepository).should(never()).save(any());
        }

        @Test
        void givenCategoryId_whenFindCategoryById_thenShouldGetSuccess() {
            //Given
            Integer categoryid = 28;
            CategoryDto categoryDto = CategoryDto.builder()
                    .id(categoryid)
                    .categoryCode("Cat test")
                    .designation("Designation test")
                    .build();
            
            given(categoryRepository.findById(categoryid)).willReturn(Optional.of(CategoryDto.toEntity(categoryDto)));

            //When
            underTest.findById(categoryid);

            //Then
            then(categoryRepository).should().findById(categoryid);
        }

        @Test
        void givenNullForCategoryId_whenFindCategoryById_thenShouldntDoAnything() {
            //Given
            Integer categoryOrderId = null;

            //when
            underTest.findById(categoryOrderId);

            //then
            then(categoryRepository).should(never()).findById(any());


        }

        @Test
        void givenCategoryId_whenFindCategoryById_thenShouldThrowEntityNotFoundException(){
            //Given
            Integer categoryId = 28;
            given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

            //When
            //Then
            assertThatThrownBy(()->underTest.findById(categoryId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Aucune catégorie avec l'id = " + categoryId);
            then(categoryRepository).should().findById(categoryId);
        }

        @Test
        void givenCategoryCode_whenFindCategoryByCode_thenShouldGetSuccess() {
            //Given
            String categoryCode = UUID.randomUUID().toString();
            CategoryDto categoryDto = CategoryDto.builder()
                    .categoryCode(categoryCode)
                    .designation("Designation test")
                    .build();
            given(categoryRepository.findByCategoryCode(categoryCode)).willReturn(Optional.of(CategoryDto.toEntity(categoryDto)));

            //When
            underTest.findByCategoryCode(categoryCode);

            //Then
            then(categoryRepository).should().findByCategoryCode(categoryCode);
        }

        @Test
        void givenNullForCategoryCode_whenFindCategoryByCode_thenShouldntDoAnything() {
            //Given
            String categoryCode = null;

            //when
            underTest.findByCategoryCode(categoryCode);

            //then
            then(categoryRepository).should(never()).findByCategoryCode(any());


        }

        @Test
        void givenCategoryCode_whenFindCategoryByCode_thenShouldThrowEntityNotFoundException(){
            //Given
            String categoryCode = "IT-28";
            given(categoryRepository.findByCategoryCode(categoryCode)).willReturn(Optional.empty());

            //When
            //Then
            assertThatThrownBy(()->underTest.findByCategoryCode(categoryCode))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Aucune catégorie avec le code = " + categoryCode);
            then(categoryRepository).should().findByCategoryCode(categoryCode);
        }

        @Test
        void whenFindAllCategorys_thenShouldGetSuccess() {
            //When
            underTest.findAll();

            //Then
            then(categoryRepository).should().findAll();
        }

        @Test
        void givenCategoryId_whenDeleteCategoryById_thenShouldSaveSuccess() {
            //Given
            Integer categoryId = 28;
            given(categoryRepository.existsById(categoryId)).willReturn(true);

            //When
            underTest.delete(categoryId);

            //Then
            then(categoryRepository).should().existsById(categoryId);
            then(categoryRepository).should().deleteById(categoryId);
        }

        @Test
        void givenNullForCategoryId_whenDeleteCategoryById_thenShouldDoAnything(){
            //Given
            Integer categoryId = null;

            //When
            underTest.delete(categoryId);

            //Then
            then(categoryRepository).should(never()).deleteById(any());
            then(categoryRepository).should(never()).existsById(any());
        }

        @Test
        void givenCategoryId_whenDeleteCategoryById_thenShouldThrowEntityNotFoundException (){
            //Given
            Integer categoryId = 28;
            given(categoryRepository.existsById(categoryId)).willReturn(false);

            //When
            //Then
            assertThatThrownBy(()->underTest.delete(categoryId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage("Aucune catégorie avec l'id = " + categoryId);
            then(categoryRepository).should().existsById(categoryId);
            then(categoryRepository).should(never()).deleteById(any());
        }
}
