package com.atm.management.api.domain.dtos;

import lombok.Data;

@Data
public class CashDepositResponseDto {

    private String message;
    private long availableBalance;

}
