package com.atm.management.api.domain.controllers;

import com.atm.management.api.domain.dtos.CashDepositResponseDto;
import com.atm.management.api.domain.services.CashDepositService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CashDepositControllerTest {

    @InjectMocks
    CashDepositController cashDepositController;

    @Mock
    CashDepositService cashDepositService;

    @Test
    public void depositCashTest() {

        String debitCardNumber = "3333222211110000";
        int pin = 1234;
        long amount = 5000;
        CashDepositResponseDto responseDto = new CashDepositResponseDto();
        responseDto.setMessage("Deposit Successful");
        responseDto.setAvailableBalance(15000);
        when(cashDepositService.depositCash(debitCardNumber, pin, amount)).thenReturn(responseDto);

        ResponseEntity<CashDepositResponseDto> response = cashDepositController.depositCash(debitCardNumber, pin, amount);
        assertEquals("Deposit Successful", response.getBody().getMessage());
        assertEquals(15000, response.getBody().getAvailableBalance());
    }
}
