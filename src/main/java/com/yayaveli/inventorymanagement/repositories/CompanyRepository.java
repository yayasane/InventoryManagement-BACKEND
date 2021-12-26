package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
