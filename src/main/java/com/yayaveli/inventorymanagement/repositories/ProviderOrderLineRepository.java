package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.ProviderOrderLine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderOrderLineRepository extends JpaRepository<ProviderOrderLine, Integer> {

}
