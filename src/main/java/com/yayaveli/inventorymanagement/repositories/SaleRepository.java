package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

}
