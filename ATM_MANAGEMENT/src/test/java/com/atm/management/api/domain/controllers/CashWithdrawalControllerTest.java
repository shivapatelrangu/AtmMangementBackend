package com.atm.management.api.domain.controllers;

import com.atm.management.api.domain.dtos.CashWithdrawalResponseDto;
import com.atm.management.api.domain.exceptions.TransactionException;
import com.atm.management.api.domain.services.CashWithdrawalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CashWithdrawalControllerTest {

    @InjectMocks
    CashWithdrawalController cashWithdrawalController;

    @Mock
    CashWithdrawalService cashWithdrawalService;

    @Test
    public void testCashWithdrawalSucces() throws TransactionException {
        String debitCardNumber = "3333222211110000";
        int pin = 1234;
        long amount = 1000;
        CashWithdrawalResponseDto responseDto = new CashWithdrawalResponseDto();
        responseDto.setAvailableBalance(2000);
        responseDto.setMesaage("Transaction Successfull");
        when(cashWithdrawalService.getCash(debitCardNumber,pin,amount)).thenReturn(responseDto);
        ResponseEntity<CashWithdrawalResponseDto> response = cashWithdrawalController.getCash(debitCardNumber,pin,amount);
        assertEquals(response.getBody().getAvailableBalance(),2000);
        assertEquals(response.getBody().getMesaage(),"Transaction Successfull");
    }
}
