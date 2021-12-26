package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.Client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
