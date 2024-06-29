package com.aquariux.cryptotradingsystem.domain.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long        id;
    @ManyToOne
    private User        user;
    @ManyToOne
    private TradingPair pair;
    private String      type;
    private Double      price;
    private Double      amount;
    private Timestamp   timestamp;
}

