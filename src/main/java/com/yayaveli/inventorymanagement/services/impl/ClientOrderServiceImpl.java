package com.yayaveli.inventorymanagement.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.ClientOrderDto;
import com.yayaveli.inventorymanagement.dto.ClientOrderLineDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Client;
import com.yayaveli.inventorymanagement.models.ClientOrder;
import com.yayaveli.inventorymanagement.models.ClientOrderLine;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.repositories.ClientOrderLineRepository;
import com.yayaveli.inventorymanagement.repositories.ClientOrderRepository;
import com.yayaveli.inventorymanagement.repositories.ClientRepository;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
import com.yayaveli.inventorymanagement.services.ClientOrderService;
import com.yayaveli.inventorymanagement.validators.ClientOrderValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientOrderServiceImpl implements ClientOrderService {
    private ClientOrderRepository clientOrderRepository;
    private ClientOrderLineRepository clientOrderLineRepository;
    private ClientRepository clientRepository;
    private ItemRepository itemRepository;

    @Autowired

    public ClientOrderServiceImpl(ClientOrderRepository clientOrderRepository,
            ClientOrderLineRepository clientOrderLineRepository, ClientRepository clientRepository,
            ItemRepository itemRepository) {
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderLineRepository = clientOrderLineRepository;
        this.clientRepository = clientRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public ClientOrderDto save(ClientOrderDto clientOrderDto) {
        // Validation du commande client
        List<String> errors = ClientOrderValidator.validate(clientOrderDto);
        if (!errors.isEmpty()) {
            log.error("Category i not valid {}", clientOrderDto);
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.CLIENT_ORDER_NOT_VALID,
                    errors);
        }
        // Vérification de l'existence du client de la commande dans la bdd
        Optional<Client> client = clientRepository.findById(clientOrderDto.getClientDto().getId());
        if (client.isEmpty()) {
            log.error("Client with ID {} was not found ",
                    clientOrderDto.getClientDto().getId());
            throw new EntityNotFoundException(
                    "Aucun client avec l'ID " + clientOrderDto.getClientDto().getId() + " n'a été trouvé dans la BD",
                    ErrorCodes.CLIENT_NOT_FOUND);
        }

        // Vérification de l'existence des articles dans la BD

        List<String> itemErrors = new ArrayList<>();

        if (clientOrderDto.getClientOrderLines() != null) {
            clientOrderDto.getClientOrderLines().forEach(orderLine -> {
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
        ClientOrder savedOrder = clientOrderRepository.save(ClientOrderDto.toEntity(
                clientOrderDto));
        // Sauvegarde des lignes de commandes dans la BD
        if (clientOrderDto.getClientOrderLines() != null) {

            clientOrderDto.getClientOrderLines().forEach(orderLine -> {
                ClientOrderLine clientOrderLine = ClientOrderLineDto.toEntity(orderLine);
                clientOrderLine.setClientOrder(savedOrder);
                clientOrderLineRepository.save(clientOrderLine);
            });
        }
        /*
         * return
         * ClientOrderDto.fromEntity(clientOrderRepository.save(ClientOrderDto.toEntity(
         * clientOrderDto)));
         */

        // Retour de de la commande client avec les données principales

        return ClientOrderDto.fromEntity(savedOrder);

    }

    @Override
    public ClientOrderDto findById(Integer id) {
        if (id == null) {
            log.error("Order id is null");
            return null;
        }
        Optional<ClientOrder> clientOrder = clientOrderRepository.findById(id);

        ClientOrderDto clientOrderDto = ClientOrderDto.fromEntity(clientOrder.get());

        return Optional.of(clientOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande avec l'id = " + id,
                        ErrorCodes.CLIENT_ORDER_NOT_FOUND));
    }

    @Override
    public ClientOrderDto findByOrderCode(String orderCode) {
        if (!StringUtils.hasLength(orderCode)) {
            log.error("orderCode is null");
            return null;
        }
        Optional<ClientOrder> clientOrder = clientOrderRepository.findByOrderCode(orderCode);

        ClientOrderDto clientOrderDto = ClientOrderDto.fromEntity(clientOrder.get());

        return Optional.of(clientOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande avec le code = " + orderCode,
                        ErrorCodes.CLIENT_ORDER_NOT_FOUND));
    }

    @Override
    public List<ClientOrderDto> findAll() {
        return clientOrderRepository.findAll().stream().map(ClientOrderDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void deleteInteger(Integer id) {
        if (id == null) {
            log.error("id is null");
            return;
        }
        clientOrderRepository.deleteById(id);

    }

}
