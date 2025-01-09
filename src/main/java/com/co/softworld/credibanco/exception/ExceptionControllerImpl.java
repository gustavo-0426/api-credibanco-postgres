package com.co.softworld.credibanco.exception;

import com.co.softworld.credibanco.model.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;

import static com.co.softworld.credibanco.util.IUtility.EMPTY;
import static com.co.softworld.credibanco.util.IUtility.FORMAT_DATETIME;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionControllerImpl implements IExceptionController {

    @Autowired(required = false)
    private Error error;

    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> violationException(MethodArgumentNotValidException notValidException, HttpServletRequest request) {
        error.setDate(now().format(FORMAT_DATETIME));
        error.setStatus(BAD_REQUEST.value());
        error.setPatch(request.getServletPath());
        error.setMessage(notValidException.getFieldError() != null ? notValidException.getFieldError().getDefaultMessage() : EMPTY);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> typeMismatchException(MethodArgumentTypeMismatchException matchException, HttpServletRequest request) {
        error.setDate(now().format(FORMAT_DATETIME));
        error.setStatus(BAD_REQUEST.value());
        error.setPatch(request.getServletPath());
        error.setMessage(matchException.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Error> transactionException(InvalidTransactionException transactionException, HttpServletRequest request) {
        error.setDate(now().format(FORMAT_DATETIME));
        error.setStatus(BAD_REQUEST.value());
        error.setPatch(request.getServletPath());
        error.setMessage(transactionException.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<Error> productException(InvalidProductException productException, HttpServletRequest request) {
        error.setDate(now().format(FORMAT_DATETIME));
        error.setStatus(BAD_REQUEST.value());
        error.setPatch(request.getServletPath());
        error.setMessage(productException.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<Error> cardException(InvalidCardException cardException, HttpServletRequest request) {
        error.setDate(now().format(FORMAT_DATETIME));
        error.setStatus(BAD_REQUEST.value());
        error.setPatch(request.getServletPath());
        error.setMessage(cardException.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Error> sqlException(SQLException sqlException, HttpServletRequest request) {
        error.setDate(now().format(FORMAT_DATETIME));
        error.setStatus(BAD_REQUEST.value());
        error.setPatch(request.getServletPath());
        error.setMessage(sqlException.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @Override
    @ExceptionHandler(InvalidCustomerException.class)
    public ResponseEntity<Error> customerException(InvalidCustomerException invalidCustomerException, HttpServletRequest request) {
        error.setDate(now().format(FORMAT_DATETIME));
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setPatch(request.getServletPath());
        error.setMessage(invalidCustomerException.getMessage());
        return new ResponseEntity<>(error, NOT_FOUND);
    }

}
