package com.yayaveli.inventorymanagement.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.ProviderOrderDto;
import com.yayaveli.inventorymanagement.dto.ProviderOrderLineDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.models.Provider;
import com.yayaveli.inventorymanagement.models.ProviderOrder;
import com.yayaveli.inventorymanagement.models.ProviderOrderLine;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
import com.yayaveli.inventorymanagement.repositories.ProviderOrderLineRepository;
import com.yayaveli.inventorymanagement.repositories.ProviderOrderRepository;
import com.yayaveli.inventorymanagement.repositories.ProviderRepository;
import com.yayaveli.inventorymanagement.services.ProviderOrderService;
import com.yayaveli.inventorymanagement.validators.ProviderOrderValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProviderOrderServiceImpl implements ProviderOrderService {
    private ProviderOrderRepository providerOrderRepository;
    private ProviderOrderLineRepository providerOrderLineRepository;
    private ProviderRepository providerRepository;
    private ItemRepository itemRepository;

    @Autowired

    public ProviderOrderServiceImpl(ProviderOrderRepository providerOrderRepository,
            ProviderOrderLineRepository providerOrderLineRepository, ProviderRepository providerRepository,
            ItemRepository itemRepository) {
        this.providerOrderRepository = providerOrderRepository;
        this.providerOrderLineRepository = providerOrderLineRepository;
        this.providerRepository = providerRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ProviderOrderDto save(ProviderOrderDto providerOrderDto) {
        // Validation du commande provider
        List<String> errors = ProviderOrderValidator.validate(providerOrderDto);
        if (!errors.isEmpty()) {
            log.error("Category i not valid {}", providerOrderDto);
            throw new InvalidEntityException("La commande fournisseur n'est pas valide",
                    ErrorCodes.PROVIDER_ORDER_NOT_VALID,
                    errors);
        }
        // Vérification de l'existence du provider de la commande dans la bdd
        Optional<Provider> provider = providerRepository.findById(providerOrderDto.getProviderDto().getId());
        if (provider.isEmpty()) {
            log.error("Fournisseur with ID {} was not found ",
                    providerOrderDto.getProviderDto().getId());
            throw new EntityNotFoundException(
                    "Aucun fournisseur avec l'ID " + providerOrderDto.getProviderDto().getId()
                            + " n'a été trouvé dans la BD",
                    ErrorCodes.PROVIDER_NOT_FOUND);
        }

        // Vérification de l'existence des articles dans la BD

        List<String> itemErrors = new ArrayList<>();

        if (providerOrderDto.getProviderOrderLines() != null) {
            providerOrderDto.getProviderOrderLines().forEach(orderLine -> {
                if (orderLine.getItemDto() != null) {
                    Optional<Item> item = itemRepository.findById(orderLine.getItemDto().getId());

                    if (item.isEmpty()) {
                        itemErrors.add("L'article avec l'ID " + orderLine.getItemDto().getId() + "n'existe pas");
                    }

                } else {
                    itemErrors.add("Impossible d'enregistrer une commande avec un article null");
                }
            });
        }

        if (itemErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Article n'existe dans la base de données", ErrorCodes.ITEM_NOT_FOUND,
                    itemErrors);
        }

        // Sauvegarde de la commande dans la bd
        ProviderOrder savedOrder = providerOrderRepository.save(ProviderOrderDto.toEntity(
                providerOrderDto));
        // Sauvegarde des lignes de commandes dans la BD
        if (providerOrderDto.getProviderOrderLines() != null) {

            providerOrderDto.getProviderOrderLines().forEach(orderLine -> {
                ProviderOrderLine providerOrderLine = ProviderOrderLineDto.toEntity(orderLine);
                providerOrderLine.setProviderOrder(savedOrder);
                providerOrderLineRepository.save(providerOrderLine);
            });
        }
        /*
         * return
         * ProviderOrderDto.fromEntity(providerOrderRepository.save(ProviderOrderDto.
         * toEntity(
         * providerOrderDto)));
         */

        // Retour de de la commande provider avec les données principales

        return ProviderOrderDto.fromEntity(savedOrder);

    }

    @Override
    public ProviderOrderDto findById(Integer id) {
        if (id == null) {
            log.error("Order id is null");
            return null;
        }
        Optional<ProviderOrder> providerOrder = providerOrderRepository.findById(id);

        ProviderOrderDto providerOrderDto = ProviderOrderDto.fromEntity(providerOrder.get());

        return Optional.of(providerOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande avec l'id = " + id,
                        ErrorCodes.PROVIDER_ORDER_NOT_FOUND));
    }

    @Override
    public ProviderOrderDto findByOrderCode(String orderCode) {
        if (!StringUtils.hasLength(orderCode)) {
            log.error("orderCode is null");
            return null;
        }
        Optional<ProviderOrder> providerOrder = providerOrderRepository.findByOrderCode(orderCode);

        ProviderOrderDto providerOrderDto = ProviderOrderDto.fromEntity(providerOrder.get());

        return Optional.of(providerOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande avec le code = " + orderCode,
                        ErrorCodes.PROVIDER_ORDER_NOT_FOUND));
    }

    @Override
    public List<ProviderOrderDto> findAll() {
        return providerOrderRepository.findAll().stream().map(ProviderOrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInteger(Integer id) {
        if (id == null) {
            log.error("id is null");
            return;
        }
        providerOrderRepository.deleteById(id);

    }
}
