package com.co.softworld.credibanco.exception;

import com.co.softworld.credibanco.model.Error;
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
import java.util.Optional;

import static com.co.softworld.credibanco.util.IUtility.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

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
        Error error = exceptionController.transactionException(transactionException, request).getBody();
        String message = Optional.ofNullable(error).orElse(new Error()).getMessage();
        assertThat(message, equalTo(TRANSACTION_INVALID_EXPIRY_CARD));
    }

    @Test
    void testProductException() {
        Error error = exceptionController.productException(productException, request).getBody();
        String message = Optional.ofNullable(error).orElse(new Error()).getMessage();
        assertThat(message, equalTo(PRODUCT_NOT_FOUND));
    }

    @Test
    void testCardException() {
        Error error = exceptionController.cardException(cardException, request).getBody();
        String message = Optional.ofNullable(error).orElse(new Error()).getMessage();
        assertThat(message, equalTo(CARD_NOT_FOUND_OR_IS_INACTIVE));
    }

    @Test
    void testSqlException() {
        exceptionController.sqlException(sqlException, request);
        verify(request).getServletPath();
    }
}