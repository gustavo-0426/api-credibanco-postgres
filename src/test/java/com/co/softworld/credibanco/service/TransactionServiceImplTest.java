package com.co.softworld.credibanco.service;

import com.co.softworld.credibanco.model.Card;
import com.co.softworld.credibanco.model.Product;
import com.co.softworld.credibanco.model.TransactionManager;
import com.co.softworld.credibanco.model.TransactionMapper;
import com.co.softworld.credibanco.repository.ICardRepository;
import com.co.softworld.credibanco.repository.ITransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.co.softworld.credibanco.util.IUtility.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceImplTest {

    @Autowired
    private ITransactionService transactionService;
    @MockitoBean
    private ITransactionRepository transactionRepositoryMock;
    @MockitoBean
    private ICardRepository cardRepositoryMock;
    private int id;
    private TransactionManager transactionSpy;
    private TransactionMapper transactionMapperSpy;

    @BeforeEach
    void setUp() {
        id = 1;
        Card cardSpy = spy(new Card());
        cardSpy.setCardId(id);
        cardSpy.setNumber("1000001234567890");
        cardSpy.setProduct(new Product());
        cardSpy.setExpiryDate(LocalDate.now().plusYears(3).format(FORMAT_DATE));
        cardSpy.setBalance(300);
        cardSpy.setActive(1);

        transactionSpy = spy(new TransactionManager());
        transactionSpy.setTransactionId(id);
        transactionSpy.setDate(LocalDateTime.now().format(FORMAT_DATETIME));
        transactionSpy.setCard(cardSpy);
        transactionSpy.setPrice(100);
        transactionSpy.setStatus(ACTIVE);

        transactionMapperSpy = spy(new TransactionMapper());
        transactionMapperSpy.setCardId(id);
        transactionMapperSpy.setTransactionId(id);
        transactionMapperSpy.setPrice(200);

        when(cardRepositoryMock.findByCardIdActive(id)).thenReturn(Optional.of(cardSpy));
        when(transactionRepositoryMock.findById(id)).thenReturn(Optional.of(transactionSpy));

    }

    @AfterEach
    void tearDown() {
        transactionService = null;
        transactionRepositoryMock = null;
        cardRepositoryMock = null;
        transactionSpy = null;
        transactionMapperSpy = null;
    }

    @Test
    void testPurchase() {
        transactionService.purchase(transactionMapperSpy);
        verify(transactionRepositoryMock).save(any());
    }

    @Test
    void testGetPurchase() {
        assertThat(transactionService.getPurchase(id).getBody(), equalTo(transactionSpy));
    }

    @Test
    void testAnnulation() {
        transactionService.annulation(transactionMapperSpy);
        assertThat(transactionSpy.getStatus(), equalTo(ANNULLED));
    }

    @Test
    void testFindAll() {
        transactionService.findAll();
        verify(transactionRepositoryMock).findAll();
    }
}