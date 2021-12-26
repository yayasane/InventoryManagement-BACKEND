package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleLineRepository extends JpaRepository<Sale, Integer> {

}
