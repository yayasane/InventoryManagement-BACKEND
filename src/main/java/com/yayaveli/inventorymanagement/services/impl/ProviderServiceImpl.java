package com.yayaveli.inventorymanagement.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.ProviderDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Provider;
import com.yayaveli.inventorymanagement.repositories.ProviderRepository;
import com.yayaveli.inventorymanagement.services.ProviderService;
import com.yayaveli.inventorymanagement.validators.ProviderValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProviderServiceImpl implements ProviderService {
    private ProviderRepository providerRepository;

    @Autowired
    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public ProviderDto save(ProviderDto providerDto) {
        List<String> errors = ProviderValidator.validate(providerDto);
        if (!errors.isEmpty()) {
            log.error("Provider i not valid {}", providerDto);
            throw new InvalidEntityException("L'artcle n'est pas valide", ErrorCodes.PROVIDER_NOT_VALID, errors);
        }
        return ProviderDto.fromEntity(providerRepository.save(ProviderDto.toEntity(providerDto)));
    }

    @Override
    public ProviderDto findById(Integer id) {
        if (id == null) {
            log.error("Provider is null");
            return null;
        }
        Optional<Provider> provider = providerRepository.findById(id);

        ProviderDto providerDto = ProviderDto.fromEntity(provider.get());

        return Optional.of(providerDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec l'id = " + id,
                        ErrorCodes.PROVIDER_NOT_FOUND));
    }

    @Override
    public List<ProviderDto> findAll() {
        return providerRepository.findAll().stream().map(ProviderDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void deleteInteger(Integer id) {
        if (id == null) {
            log.error("Provider is null");
            return;
        }
        providerRepository.deleteById(id);
    }
}
