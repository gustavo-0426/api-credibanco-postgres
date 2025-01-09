package com.co.softworld.credibanco.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;

import static com.co.softworld.credibanco.util.IUtility.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ExceptionControllerImplTest {

    @Autowired
    private ExceptionControllerImpl exceptionController;
    @MockitoBean
    private HttpServletRequest request;
    @MockitoBean
    private MethodArgumentNotValidException argumentNotValidException;
    @MockitoBean
    private MethodArgumentTypeMismatchException argumentTypeMismatchException;
    @MockitoBean
    private SQLException sqlException;
    private InvalidTransactionException transactionException;
    private InvalidProductException productException;
    private InvalidCardException cardException;

    @BeforeEach
    void setUp() {
        transactionException = new InvalidTransactionException(TRANSACTION_INVALID_EXPIRY_CARD);
        productException = new InvalidProductException(PRODUCT_NOT_FOUND);
        cardException = new InvalidCardException(CARD_NOT_FOUND_OR_IS_INACTIVE);
    }

    @AfterEach
    void tearDown() {
        exceptionController = null;
        request = null;
        argumentNotValidException = null;
        argumentTypeMismatchException = null;
        sqlException = null;
        transactionException = null;
        productException = null;
        cardException = null;
    }

    @Test
    void testViolationException() {
        exceptionController.violationException(argumentNotValidException, request);
        verify(request).getServletPath();
    }

    @Test
    void testTypeMismatchException() {
        exceptionController.typeMismatchException(argumentTypeMismatchException, request);
        verify(request).getServletPath();
    }

    @Test
    void testTransactionException() {
        String detail = exceptionController.transactionException(transactionException, request).getDetail();
        assertThat(detail, equalTo(TRANSACTION_INVALID_EXPIRY_CARD));
    }

    @Test
    void testProductException() {
        String detail = exceptionController.productException(productException, request).getDetail();
        assertThat(detail, equalTo(PRODUCT_NOT_FOUND));
    }

    @Test
    void testCardException() {
        String detail = exceptionController.cardException(cardException, request).getDetail();
        assertThat(detail, equalTo(CARD_NOT_FOUND_OR_IS_INACTIVE));
    }

    @Test
    void testSqlException() {
        exceptionController.sqlException(sqlException, request);
        verify(request).getServletPath();
    }
}