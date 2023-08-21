package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.ClientOrderLine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrderLineRepository extends JpaRepository<ClientOrderLine, Integer> {

}
