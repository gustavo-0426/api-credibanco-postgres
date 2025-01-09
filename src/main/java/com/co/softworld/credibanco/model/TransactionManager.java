package com.co.softworld.credibanco.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "transaction_mg")
@Data
public class TransactionManager {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int transactionId;
    private String date;
    @ManyToOne()
    private Card card;
    private double price;
    private String status;
}
