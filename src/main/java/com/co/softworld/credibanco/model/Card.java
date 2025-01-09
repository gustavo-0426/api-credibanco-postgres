package com.co.softworld.credibanco.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int cardId;
    private String number;
    @ManyToOne()
    private Product product;
    private String expiryDate;
    private double balance;
    private int active;
}
