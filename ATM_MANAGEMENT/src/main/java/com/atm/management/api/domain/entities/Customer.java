package com.atm.management.api.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String customerName;
    private String phoneNumber;
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private List<DebitCard> debitCard;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private List<TransactionDetails> transactionDetails;

}

