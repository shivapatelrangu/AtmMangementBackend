package com.atm.management.api.domain.controllers;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.*;

import com.atm.management.api.domain.dtos.BalanceEnqueryResponseDto;
import com.atm.management.api.domain.services.BalanceEnquiryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class BalanceEnquiryControllerTest {

    @InjectMocks
    BalanceEnquiryController balanceEnquiryController;

    @Mock
    BalanceEnquiryService balanceEnquiryService;

    @Test
    public void testBalanceEnquirySuccess(){

        String debitCardNumber = "9876543210987654";
        int pin = 5432;
        BalanceEnqueryResponseDto dto = new BalanceEnqueryResponseDto();
        dto.setAccountBalance(10000);
        when(balanceEnquiryService.getAccountBalance(debitCardNumber,pin)).thenReturn(dto);
        ResponseEntity<BalanceEnqueryResponseDto> response = balanceEnquiryController.getAccountBalance(debitCardNumber, pin);

        assertEquals(10000, response.getBody().getAccountBalance());

    }
}
