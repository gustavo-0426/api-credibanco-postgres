package com.co.softworld.credibanco.controller.impl;

import com.co.softworld.credibanco.controller.IProductController;
import com.co.softworld.credibanco.model.Product;
import com.co.softworld.credibanco.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${api.version}/product")
@Tag(name = "Product Controller")
@AllArgsConstructor
public class ProductControllerImpl implements IProductController {

    private final IProductService productService;

    @Override
    @PostMapping
    @Operation(summary = "Guarda el producto. Recibe en el cuerpo del mensaje el producto y lo procesa.")
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @Override
    @GetMapping("/{productId}")
    @Operation(summary = "Consulta un producto. Recibe por parámetro el id del producto, valida que el producto " +
            "exista y genera la consulta. ")
    public ResponseEntity<Product> findById(@PathVariable int productId) {
        return productService.findById(productId);
    }

    @Override
    @GetMapping
    @Operation(summary = " Consulta todos los productos creados.")
    public ResponseEntity<List<Product>> findAll() {
        return productService.findAll();
    }

    @Override
    @DeleteMapping("/{productId}")
    @Operation(summary = "Elimina un producto. Recibe por parámetro el id del producto, valida que el producto exista " +
            "y procesa la eliminación.")
    public ResponseEntity<Product> delete(@PathVariable int productId) {
        return productService.delete(productId);
    }

}