package com.co.softworld.credibanco.service.impl;

import com.co.softworld.credibanco.exception.InvalidCustomerException;
import com.co.softworld.credibanco.model.Customer;
import com.co.softworld.credibanco.repository.ICustomerRepository;
import com.co.softworld.credibanco.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.co.softworld.credibanco.util.IUtility.VALID_ROLES;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    CustomerServiceImpl(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public ResponseEntity<Customer> create(Customer customer) {

        if (customerRepository.existsByUsername(customer.getUsername()))
            throw new InvalidCustomerException("Customer already exists");

        validateRole(customer.getRoles());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Customer>> findAll() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    private void validateRole(String roles) {
        Pattern pattern = Pattern.compile(VALID_ROLES);
        Matcher matcher = pattern.matcher(roles);
        if (!matcher.find())
            throw new InvalidCustomerException("Invalid roles provided: " + roles + ", the role must start with ROLE_");
    }
}
