package com.co.softworld.credibanco.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.sql.SQLException;

import static com.co.softworld.credibanco.util.IUtility.EMPTY;
import static com.co.softworld.credibanco.util.IUtility.FORMAT_DATETIME;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionControllerImpl {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail violationException(MethodArgumentNotValidException notValidException, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(BAD_REQUEST, notValidException.getMessage());
        problemDetail.setType(URI.create(request.getServletPath() != null ? request.getServletPath() : EMPTY));
        problemDetail.setProperty("timestamp", now().format(FORMAT_DATETIME));
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail typeMismatchException(MethodArgumentTypeMismatchException matchException, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, matchException.getMessage());
        problemDetail.setType(URI.create(request.getServletPath() != null ? request.getServletPath() : EMPTY));
        problemDetail.setProperty("timestamp", now().format(FORMAT_DATETIME));
        return problemDetail;
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ProblemDetail transactionException(InvalidTransactionException transactionException, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, transactionException.getMessage());
        problemDetail.setType(URI.create(request.getServletPath() != null ? request.getServletPath() : EMPTY));
        problemDetail.setProperty("timestamp", now().format(FORMAT_DATETIME));
        return problemDetail;
    }

    @ExceptionHandler(InvalidProductException.class)
    public ProblemDetail productException(InvalidProductException productException, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, productException.getMessage());
        problemDetail.setType(URI.create(request.getServletPath() != null ? request.getServletPath() : EMPTY));
        problemDetail.setProperty("timestamp", now().format(FORMAT_DATETIME));
        return problemDetail;
    }

    @ExceptionHandler(InvalidCardException.class)
    public ProblemDetail cardException(InvalidCardException cardException, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, cardException.getMessage());
        problemDetail.setType(URI.create(request.getServletPath() != null ? request.getServletPath() : EMPTY));
        problemDetail.setProperty("timestamp", now().format(FORMAT_DATETIME));
        return problemDetail;
    }

    @ExceptionHandler(SQLException.class)
    public ProblemDetail sqlException(SQLException sqlException, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, sqlException.getMessage());
        problemDetail.setType(URI.create(request.getServletPath() != null ? request.getServletPath() : EMPTY));
        problemDetail.setProperty("timestamp", now().format(FORMAT_DATETIME));
        return problemDetail;
    }

    @ExceptionHandler(InvalidCustomerException.class)
    public ProblemDetail customerException(InvalidCustomerException invalidCustomerException, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, invalidCustomerException.getMessage());
        problemDetail.setType(URI.create(request.getServletPath() != null ? request.getServletPath() : EMPTY));
        problemDetail.setProperty("timestamp", now().format(FORMAT_DATETIME));
        return problemDetail;
    }

}
