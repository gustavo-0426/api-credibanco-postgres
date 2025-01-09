package com.co.softworld.credibanco.repository;

import com.co.softworld.credibanco.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);
    boolean existsByUsername(String username);
}
