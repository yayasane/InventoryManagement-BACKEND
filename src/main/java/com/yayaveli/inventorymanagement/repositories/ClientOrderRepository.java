package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.ClientOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {

}
