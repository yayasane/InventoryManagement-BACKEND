package com.yayaveli.inventorymanagement.repositories;

import com.yayaveli.inventorymanagement.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
