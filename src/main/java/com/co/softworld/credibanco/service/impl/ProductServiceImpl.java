package com.co.softworld.credibanco.service.impl;

import com.co.softworld.credibanco.exception.InvalidProductException;
import com.co.softworld.credibanco.model.Product;
import com.co.softworld.credibanco.repository.IProductRepository;
import com.co.softworld.credibanco.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.co.softworld.credibanco.util.IUtility.PRODUCT_NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Override
    public ResponseEntity<Product> save(Product product) {
        return new ResponseEntity<>(productRepository.save(product), OK);
    }

    @Override
    public ResponseEntity<Product> findById(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidProductException(PRODUCT_NOT_FOUND));
        return new ResponseEntity<>(product, OK);
    }

    @Override
    public ResponseEntity<List<Product>> findAll() {
        return new ResponseEntity<>(productRepository.findAll(), OK);
    }

    @Override
    public ResponseEntity<Product> delete(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidProductException(PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        return new ResponseEntity<>(product, OK);
    }

}