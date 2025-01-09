package com.co.softworld.credibanco.controller.impl;

import com.co.softworld.credibanco.controller.ICustomerController;
import com.co.softworld.credibanco.model.Customer;
import com.co.softworld.credibanco.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@Api(tags = "Customer Controller")
public class CustomerControllerImpl implements ICustomerController {

    private final ICustomerService customerService;


    @Override
    @PostMapping("/create")
    @ApiOperation(value = "Registra el cliente. Recibe en el cuerpo del mensaje el username, password sin encriptar y " +
            "los roles asociados (Deben iniciar con la palabra ROLE_) separados por coma.")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Consulta los cliente registrados.")
    public ResponseEntity<List<Customer>> findAll() {
        return customerService.findAll();
    }
}
