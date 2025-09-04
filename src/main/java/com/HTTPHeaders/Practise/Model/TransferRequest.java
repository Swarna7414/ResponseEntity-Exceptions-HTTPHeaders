package com.HTTPHeaders.Practise.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "AmountTransfers")
public class TransferRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Description;

    private Long fromAccountId;

    private Long toAccountId;

    private BigDecimal amount;
}
