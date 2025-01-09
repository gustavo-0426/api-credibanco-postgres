package com.co.softworld.credibanco.controller;

import com.co.softworld.credibanco.model.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerController {

    ResponseEntity<Customer> create(Customer customer);
    ResponseEntity<List<Customer>> findAll();
}
