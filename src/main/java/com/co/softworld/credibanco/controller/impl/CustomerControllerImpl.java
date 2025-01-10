package com.co.softworld.credibanco.controller.impl;

import com.co.softworld.credibanco.controller.ICustomerController;
import com.co.softworld.credibanco.model.Customer;
import com.co.softworld.credibanco.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.version}/customer")
@AllArgsConstructor
@Tag(name = "Customer Controller")
public class CustomerControllerImpl implements ICustomerController {

    private final ICustomerService customerService;


    @Override
    @PostMapping("/create")
    @Operation(summary = "Registra el cliente. Recibe en el cuerpo del mensaje el username, password sin encriptar y " +
            "los roles asociados (Deben iniciar con la palabra ROLE_) separados por coma.")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @Override
    @GetMapping
    @Operation(summary = "Consulta los cliente registrados.")
    public ResponseEntity<List<Customer>> findAll() {
        return customerService.findAll();
    }
}
