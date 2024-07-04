package com.atm.management.api.domain.dtos;

import lombok.Data;

@Data
public class CashWithdrawalResponseDto {

    private String mesaage;
    private long availableBalance;
}
