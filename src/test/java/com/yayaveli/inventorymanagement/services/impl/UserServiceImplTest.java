package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.AddressDto;
import com.yayaveli.inventorymanagement.dto.UserDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.User;
import com.yayaveli.inventorymanagement.repositories.UserRepository;
import com.yayaveli.inventorymanagement.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        underTest = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void givenUserDto_whenAddUser_thenShouldSaveSuccess() {
        //Given
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        UserDto userDto = UserDto.
                builder()
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .password("rangrang")
                .dateOfBirth(new Date())
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();

        System.out.println("Before ---- "+userDto);
        //When
        underTest.save(userDto);

        //Then
        assertThat(UserValidator.validate(userDto)).isEqualTo(Collections.emptyList());
        then(userRepository).should().save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(UserDto.toEntity(userDto));
        assertThat(bCryptPasswordEncoder.matches(userDto.getPassword(), capturedUser.getPassword()));
        then(userRepository).should().save(UserDto.toEntity(userDto));

    }

    @Test
    void givenUserDto_whenAddUser_thenShouldThrowInvalidEntityException(){
        //Given

        UserDto userDto = UserDto.
                builder()
                .build();

        //When
        //Then
        assertThatThrownBy(()->underTest.save(userDto))
                .isInstanceOf(InvalidEntityException.class)
                .hasMessage("L'utilisateur n'est pas valide");
        assertThat(UserValidator.validate(userDto)).isNotEqualTo(Collections.emptyList());
        then(userRepository).should(never()).save(any());
    }

    @Test
    void givenUserId_whenFindUserById_thenShouldGetSuccess() {
        //Given
        Integer userid = 28;
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        UserDto userDto = UserDto.
                builder()
                .id(userid)
                .firstName("Yaya")
                .lastName("SANE")
                .email("yabadji2010@gmail.com")
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();
        given(userRepository.findById(userid)).willReturn(Optional.of(UserDto.toEntity(userDto)));

        //When
        underTest.findById(userid);

        //Then
        then(userRepository).should().findById(userid);
    }

    @Test
    void givenNullForUserId_whenFindUserById_thenShouldntDoAnything() {
        //Given
        Integer userOrderId = null;

        //when
        underTest.findById(userOrderId);

        //then
        then(userRepository).should(never()).findById(any());


    }

    @Test
    void givenUserId_whenFindUserById_thenShouldThrowEntityNotFoundException(){
        //Given
        Integer userId = 28;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findById(userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun utilisateur avec l'id = " + userId);
        then(userRepository).should().findById(userId);
    }

    @Test
    void givenUserEmail_whenFindUserByEmail_thenShouldGetSuccess() {
        //Given
        String userEmail = "yabadji2010@gmail.com";
        AddressDto addressDto = AddressDto
                .builder()
                .address1("Keur Massar")
                .city("Dakar")
                .zipCode("12000")
                .country("Sénégal")
                .build();

        UserDto userDto = UserDto.
                builder()
                .id(28)
                .firstName("Yaya")
                .lastName("SANE")
                .email(userEmail)
                .phoneNumber("781255000")
                .addressDto(addressDto)
                .build();
        given(userRepository.findByEmail(userEmail)).willReturn(Optional.of(UserDto.toEntity(userDto)));

        //When
        underTest.findByEmail(userEmail);

        //Then
        then(userRepository).should().findByEmail(userEmail);
    }

    @Test
    void givenUserEmail_whenFindUserByEmail_thenShouldThrowEntityNotFoundException(){
        //Given
        String userEmail = "yabadji2010@gmail.com";
        given(userRepository.findByEmail(userEmail)).willReturn(Optional.empty());

        //When
        //Then
        assertThatThrownBy(()->underTest.findByEmail(userEmail))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun utilisateur avec l'eamil= " + userEmail);
        then(userRepository).should().findByEmail(userEmail);
    }

    @Test
    void whenFindAllUsers_thenShouldGetSuccess() {
        //When
        underTest.findAll();

        //Then
        then(userRepository).should().findAll();
    }

    @Test
    void givenUserId_whenDeleteUserById_thenShouldSaveSuccess() {
        //Given
        Integer userId = 28;
        given(userRepository.existsById(userId)).willReturn(true);

        //When
        underTest.delete(userId);

        //Then
        then(userRepository).should().existsById(userId);
        then(userRepository).should().deleteById(userId);
    }

    @Test
    void givenNullForUserId_whenDeleteUserById_thenShouldntDoAnything(){
        //Given
        Integer userId = null;

        //When
        underTest.delete(userId);

        //Then
        then(userRepository).should(never()).deleteById(any());
        then(userRepository).should(never()).existsById(any());
    }

    @Test
    void givenUserId_whenDeleteUserById_thenShouldThrowEntityNotFoundException (){
        //Given
        Integer userId = 28;
        given(userRepository.existsById(userId)).willReturn(false);

        //When
        //Then
        assertThatThrownBy(()->underTest.delete(userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aucun utilisateur avec l'id = " + userId);
        then(userRepository).should().existsById(userId);
        then(userRepository).should(never()).deleteById(any());
    }


}