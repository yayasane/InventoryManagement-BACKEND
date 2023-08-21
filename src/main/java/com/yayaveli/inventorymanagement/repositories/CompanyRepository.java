package com.yayaveli.inventorymanagement.repositories;

import java.util.Optional;

import com.yayaveli.inventorymanagement.models.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByName(String name);
}
