package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.yayaveli.inventorymanagement.dto.ClientDto;
import com.yayaveli.inventorymanagement.dto.UserDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.User;
import com.yayaveli.inventorymanagement.repositories.UserRepository;
import com.yayaveli.inventorymanagement.services.UserService;
import com.yayaveli.inventorymanagement.validators.UserValidator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto save(UserDto userDto) {
        List<String> errors = UserValidator.validate(userDto);
        if (!errors.isEmpty()) {
            log.error("User is not valid {}", errors);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.USER_NOT_VALID, errors);
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return UserDto.fromEntity(userRepository.save(UserDto.toEntity(userDto)));
    }

    @Override
    public UserDto findById(Integer id) {
        if (id == null) {
            log.error("User is null");
            return null;
        }
        return userRepository.findById(id).map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun utilisateur avec l'id = " + id,
                        ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("User id is null");
            return;
        }
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Aucun utilisateur avec l'id = " + id,
                    ErrorCodes.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findByEmail(String email) {
        System.err.println(email + "-------------");
        return userRepository.findByEmail(email).map(UserDto::fromEntity).orElseThrow(
                () -> new EntityNotFoundException("Aucun utilisateur avec l'eamil= " + email, ErrorCodes.USER_NOT_FOUND));
    }
}
