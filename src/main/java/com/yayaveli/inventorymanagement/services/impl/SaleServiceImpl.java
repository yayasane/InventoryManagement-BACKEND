package com.yayaveli.inventorymanagement.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yayaveli.inventorymanagement.dto.SaleDto;
import com.yayaveli.inventorymanagement.dto.SaleLineDto;
import com.yayaveli.inventorymanagement.exceptions.EntityNotFoundException;
import com.yayaveli.inventorymanagement.exceptions.ErrorCodes;
import com.yayaveli.inventorymanagement.exceptions.InvalidEntityException;
import com.yayaveli.inventorymanagement.models.Item;
import com.yayaveli.inventorymanagement.models.Sale;
import com.yayaveli.inventorymanagement.models.SaleLine;
import com.yayaveli.inventorymanagement.repositories.ClientRepository;
import com.yayaveli.inventorymanagement.repositories.ItemRepository;
import com.yayaveli.inventorymanagement.repositories.SaleLineRepository;
import com.yayaveli.inventorymanagement.repositories.SaleRepository;
import com.yayaveli.inventorymanagement.services.SaleService;
import com.yayaveli.inventorymanagement.validators.SaleValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SaleServiceImpl implements SaleService {
    private SaleRepository saleRepository;
    private SaleLineRepository saleLineRepository;
    private ClientRepository clientRepository;
    private ItemRepository itemRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository,
            SaleLineRepository saleLineRepository, ClientRepository clientRepository,
            ItemRepository itemRepository) {
        this.saleRepository = saleRepository;
        this.saleLineRepository = saleLineRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public SaleDto save(SaleDto saleDto) {
        // Validation du vente client
        List<String> errors = SaleValidator.validate(saleDto);
        if (!errors.isEmpty()) {
            log.error("Sale is not valid {}", saleDto);
            throw new InvalidEntityException("La vente client n'est pas valide", ErrorCodes.SALE_NOT_VALID,
                    errors);
        }

        // Vérification de l'existence des articles dans la BD

        List<String> itemErrors = new ArrayList<>();

        if (saleDto.getSaleLineDtos() != null) {
            saleDto.getSaleLineDtos().forEach(saleLine -> {
                if (saleLine.getItemDto() != null) {
                    Optional<Item> item = itemRepository.findById(saleLine.getItemDto().getId());

                    if (item.isEmpty()) {
                        itemErrors.add("L'article avec l'ID " + saleLine.getItemDto().getId() + "n'existe pas");
                    }

                } else {
                    itemErrors.add("Impossible d'enregistrer une vente avec un article null");
                }
            });
        }

        if (itemErrors.isEmpty()) {
            log.warn("One or more items are not find in the DB, {}", itemErrors);
            throw new InvalidEntityException("Un ou plusieur articles n'ont pas été trouvés dans la base de données",
                    ErrorCodes.SALE_NOT_VALID,
                    itemErrors);
        }

        // Sauvegarde de la vente dans la bd
        Sale savedSale = saleRepository.save(SaleDto.toEntity(
                saleDto));
        // Sauvegarde des lignes de ventes dans la BD
        if (saleDto.getSaleLineDtos() != null) {

            saleDto.getSaleLineDtos().forEach(saleLineDto -> {
                SaleLine saleLine = SaleLineDto.toEntity(saleLineDto);
                saleLine.setSale(savedSale);
                saleLineRepository.save(saleLine);
            });
        }
        /*
         * return
         * SaleDto.fromEntity(saleRepository.save(SaleDto.toEntity(
         * saleDto)));
         */

        // Retour de de la vente client avec les données principales

        return SaleDto.fromEntity(savedSale);

    }

    @Override
    public SaleDto findById(Integer id) {
        if (id == null) {
            log.error("Sale id is null");
            return null;
        }
        Optional<Sale> sale = saleRepository.findById(id);

        SaleDto saleDto = SaleDto.fromEntity(sale.get());

        return Optional.of(saleDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente avec l'id = " + id,
                        ErrorCodes.SALE_NOT_FOUND));
    }

    @Override
    public SaleDto findBySaleCode(String saleCode) {
        if (!StringUtils.hasLength(saleCode)) {
            log.error("saleCode is null");
            return null;
        }
        Optional<Sale> sale = saleRepository.findBySaleCode(saleCode);

        SaleDto saleDto = SaleDto.fromEntity(sale.get());

        return Optional.of(saleDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente avec le code = " + saleCode,
                        ErrorCodes.SALE_NOT_FOUND));
    }

    @Override
    public List<SaleDto> findAll() {
        return saleRepository.findAll().stream().map(SaleDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void deleteInteger(Integer id) {
        if (id == null) {
            log.error("id is null");
            return;
        }
        saleRepository.deleteById(id);

    }
}
