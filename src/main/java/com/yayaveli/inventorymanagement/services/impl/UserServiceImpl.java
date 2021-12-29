package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.UserDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.User;
import com.yayaveli.inventorymanagement.repositories.UserRepository;
import com.yayaveli.inventorymanagement.services.UserService;
import com.yayaveli.inventorymanagement.validators.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto save(UserDto userDto) {
        List<String> errors = UserValidator.validate(userDto);
        if (!errors.isEmpty()) {
            log.error("User i not valid {}", userDto);
            throw new InvalidEntityException("L'artcle n'est pas valide", ErrorCodes.USER_NOT_VALID, errors);
        }
        return UserDto.fromEntity(userRepository.save(UserDto.toEntity(userDto)));
    }

    @Override
    public UserDto findById(Integer id) {
        if (id == null) {
            log.error("User is null");
            return null;
        }
        Optional<User> user = userRepository.findById(id);

        UserDto userDto = UserDto.fromEntity(user.get());

        return Optional.of(userDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec l'id = " + id,
                        ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void deleteInteger(Integer id) {
        if (id == null) {
            log.error("User is null");
            return;
        }
        userRepository.deleteById(id);
    }
}
