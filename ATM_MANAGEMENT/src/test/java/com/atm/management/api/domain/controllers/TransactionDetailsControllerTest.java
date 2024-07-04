package com.atm.management.api.domain.controllers;

import com.atm.management.api.domain.dtos.TransactionHistoryResponseDto;
import com.atm.management.api.domain.services.TransactionDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionDetailsControllerTest {

    @InjectMocks
    TransactionDetailsController transactionDetailsController;

    @Mock
    TransactionDetailsService transactionDetailsService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransactionHistory() {
        String debitCardNumber = "3333222211110000";
        int pin = 1234;

        TransactionHistoryResponseDto transactionHistoryResponseDto = new TransactionHistoryResponseDto();

        List<TransactionHistoryResponseDto> transactionHistoryResponseDtoList = Arrays.asList(transactionHistoryResponseDto);

        when(transactionDetailsService.getTransactionHistory(debitCardNumber, pin)).thenReturn(transactionHistoryResponseDtoList);

        ResponseEntity<List<TransactionHistoryResponseDto>> response = transactionDetailsController.getTransactionHistory(debitCardNumber, pin);

        assertEquals(transactionHistoryResponseDtoList, response.getBody());
    }
}
