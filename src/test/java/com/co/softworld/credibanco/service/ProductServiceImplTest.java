package com.co.softworld.credibanco.service;

import com.co.softworld.credibanco.model.Product;
import com.co.softworld.credibanco.repository.IProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static java.util.List.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.*;


@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private IProductService productService;
    @MockitoBean
    private IProductRepository productRepository;
    private Product productSpy;
    private int id;

    @BeforeEach
    void setUp() {
        id = 100000;
        productSpy = spy(new Product());
        productSpy.setProductId(id);
        productSpy.setName("Product Name");
        productSpy.setCustomer("Gustavo Castro");

        when(productRepository.findById(id)).thenReturn(Optional.of(productSpy));
    }

    @AfterEach
    void tearDown() {
        productService = null;
        productRepository = null;
        productSpy = null;
    }

    @Test
    void testSave() {
        productService.save(productSpy);
        verify(productRepository).save(productSpy);
    }

    @Test
    void testFindById() {
        assertThat(productService.findById(id).getBody(), equalTo(productSpy));
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(of(productSpy));
        assertThat(productService.findAll().getBody(), hasItems(productSpy));
    }

    @Test
    void testDelete() {
        productService.delete(id);
        verify(productRepository).delete(productSpy);
    }
}