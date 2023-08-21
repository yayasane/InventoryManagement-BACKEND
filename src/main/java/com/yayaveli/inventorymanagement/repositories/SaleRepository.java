package com.yayaveli.inventorymanagement.repositories;

import java.util.Optional;

import com.yayaveli.inventorymanagement.models.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    Optional<Sale> findBySaleCode(String saleCode);
}
