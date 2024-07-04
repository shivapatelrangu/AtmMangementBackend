package com.atm.management.api.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DebitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int debitCardId;

    private String DebitCardNumber;
    private String expiryDate;
    private int cvv;
    private int pin;
    private String accountNumber;
    private long accountBalance;
    private int customerId;


}
