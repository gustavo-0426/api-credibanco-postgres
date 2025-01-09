package com.co.softworld.credibanco.service;

import com.co.softworld.credibanco.model.Card;
import com.co.softworld.credibanco.model.Product;
import com.co.softworld.credibanco.repository.ICardRepository;
import com.co.softworld.credibanco.repository.IProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CardServiceImplTest {

    @MockitoBean
    private ICardRepository cardRepositoryMock;
    @MockitoBean
    private IProductRepository productRepositoryMock;
    @Autowired
    private ICardService cardService;
    private Product product;
    private Card cardSpy;
    private int id;

    @BeforeEach
    void setUp() {
        id = 100000;
        product = new Product();
        product.setProductId(id);
        product.setName("Product Name");
        product.setCustomer("Gustavo Castro");

        cardSpy = spy(new Card());
        cardSpy.setCardId(1);
        cardSpy.setProduct(product);
        cardSpy.setExpiryDate("06/2026");
        cardSpy.setActive(1);
        when(cardRepositoryMock.findByCardIdActive(1)).thenReturn(Optional.of(cardSpy));
    }

    @AfterEach
    void tearDown() {
        cardRepositoryMock = null;
        productRepositoryMock = null;
        cardService = null;
        product = null;
        cardSpy = null;
    }

    @Test
    void testGenerateCard() {
        when(productRepositoryMock.findById(id)).thenReturn(Optional.of(product));
        cardService.generateCard(id);
        verify(cardRepositoryMock).save(any());
    }

    @Test
    void testGenerateNumber() {
        assertThat(cardService.generateNumber(id), startsWith("100000"));
    }

    @Test
    void testActivateCard() {
        cardSpy = spy(new Card());
        cardSpy.setCardId(1);
        cardSpy.setActive(0);
        when(cardRepositoryMock.findByCardIdInactive(1)).thenReturn(Optional.of(cardSpy));
        cardService.activateCard(cardSpy);
        verify(cardSpy).setActive(1);
    }

    @Test
    void testBlock() {
        cardSpy.setActive(1);
        cardService.block(1);
        verify(cardSpy).setActive(0);
    }

    @Test
    void testAddBalance() {
        cardSpy.setBalance(100);
        cardService.addBalance(cardSpy);
        verify(cardSpy).setBalance(100);
    }

    @Test
    void testGetBalance() {
        cardSpy.setBalance(100);
        cardService.getBalance(1);
        verify(cardSpy).getBalance();
    }

    @Test
    void testFindAll() {
        cardService.findAll();
        verify(cardRepositoryMock).findAll();
    }

}