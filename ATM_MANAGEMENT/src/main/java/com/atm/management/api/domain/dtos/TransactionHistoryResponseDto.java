package com.atm.management.api.domain.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionHistoryResponseDto {

    private int transactionId;
    private String TransactionType;
    private String debitCardNumber;
    private long amount;
    private String transactionDate;
    private String accountNumber;

}
