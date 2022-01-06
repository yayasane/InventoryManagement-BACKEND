package com.yayaveli.inventorymanagement.services.impl;

import com.yayaveli.inventorymanagement.dto.ProviderDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.repositories.ProviderRepository;
import com.yayaveli.inventorymanagement.services.ProviderService;
import com.yayaveli.inventorymanagement.validators.ProviderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
            log.error("Provider is not valid {}", providerDto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.PROVIDER_NOT_VALID, errors);
        }
        return ProviderDto.fromEntity(providerRepository.save(ProviderDto.toEntity(providerDto)));
    }

    @Override
    public ProviderDto findById(Integer id) {
        if (id == null) {
            log.error("Provider is null");
            return null;
        }
        return providerRepository.findById(id).map(ProviderDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucun fournisseur avec l'id = " + id,
                        ErrorCodes.PROVIDER_NOT_FOUND));
    }

    @Override
    public List<ProviderDto> findAll() {
        return providerRepository.findAll().stream().map(ProviderDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Provider is null");
            return;
        }
        if(!providerRepository.existsById(id)) {
            throw new EntityNotFoundException("Aucun fournisseur avec l'id = " + id,
                    ErrorCodes.PROVIDER_NOT_FOUND);
        }
        providerRepository.deleteById(id);
    }
}
