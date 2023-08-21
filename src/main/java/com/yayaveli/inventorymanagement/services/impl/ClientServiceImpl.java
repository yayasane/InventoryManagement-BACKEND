package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.ClientDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Client;
import com.yayaveli.inventorymanagement.repositories.ClientRepository;
import com.yayaveli.inventorymanagement.services.ClientService;
import com.yayaveli.inventorymanagement.validators.ClientValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        List<String> errors = ClientValidator.validate(clientDto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", clientDto);
            throw new InvalidEntityException("L'artcle n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }
        return ClientDto.fromEntity(clientRepository.save(ClientDto.toEntity(clientDto)));
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("Client id is null");
            return null;
        }

        return clientRepository.findById(id).map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec l'id = " + id,
                        ErrorCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(ClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client id is null");
            return;
        }
        if(!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Aucun client avec l'id = " + id,
                    ErrorCodes.CLIENT_NOT_FOUND);
        }
        clientRepository.deleteById(id);
    }
}
