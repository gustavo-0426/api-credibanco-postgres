package com.co.softworld.credibanco.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.co.softworld.credibanco.util.IUtility.*;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int productId;

    @NotNull(message = "The name field must not have a null value")
    @Pattern(regexp = VALID_NAME, message = "The name field must not have special characters or be empty")
    private String name;

    @NotNull(message = "The customer field must not have a null value")
    @Pattern(regexp = VALID_NAME, message = "The customer field must not have special characters or be empty")
    private String customer;
}