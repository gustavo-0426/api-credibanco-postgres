package com.co.softworld.credibanco.service;

import com.co.softworld.credibanco.model.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerService {
    ResponseEntity<Customer> create(Customer customer);
    ResponseEntity<List<Customer>> findAll();
}
