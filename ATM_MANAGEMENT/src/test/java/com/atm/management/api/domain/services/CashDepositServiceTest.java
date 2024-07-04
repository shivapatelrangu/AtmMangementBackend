package com.atm.management.api.domain.services;

import com.atm.management.api.domain.dtos.CashDepositResponseDto;
import com.atm.management.api.domain.entities.Atm;
import com.atm.management.api.domain.entities.DebitCard;
import com.atm.management.api.domain.exceptions.ValidationException;
import com.atm.management.api.domain.repositories.AtmRepository;
import com.atm.management.api.domain.repositories.DebitCardRepository;
import com.atm.management.api.domain.repositories.TransactionDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CashDepositServiceTest {

    @InjectMocks
    CashDepositService cashDepositService;

    @Mock
    DebitCardRepository debitCardRepository;

    @Mock
    TransactionDetailsRepository transactionDetailsRepository;

    @Mock
    AtmRepository atmRepository;


    @Test
    public void testCashDepositSuccess(){
        String debitCardNumber = "3333222211110000";
        int pin = 1234 ;
        long amount = 1000;
        DebitCard debitCard = new DebitCard();
        debitCard.setPin(pin);
        debitCard.setDebitCardNumber(debitCardNumber);
        Atm atm = new Atm();
        atm.setCashAvailable(10000);
        when(debitCardRepository.findByDebitCardNumber(debitCardNumber)).thenReturn(debitCard);
        when(atmRepository.getCashAvailable()).thenReturn(atm);
        CashDepositResponseDto responseDto = cashDepositService.depositCash(debitCardNumber,pin,amount);
        assertNotNull(responseDto);
        assertEquals("Cash deposited Successfully..!",responseDto.getMessage());
        verify(debitCardRepository, times(1)).findByDebitCardNumber(debitCardNumber);
    }
    @Test
    public void testAccountBalance_InvalidCardNumber(){
        String debitCardNumber = "9876543210987652";
        int pin = 5432;
        long amount = 1000;
        DebitCard debitCard = new DebitCard();
        debitCard.setDebitCardNumber(debitCardNumber);
        debitCard.setPin(1234);
        when(debitCardRepository.findByDebitCardNumber(debitCardNumber)).thenReturn(debitCard);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            cashDepositService.depositCash(debitCardNumber,pin,amount);
        });
        assertEquals("Please enter valid pin number/card number",exception.getMessage());
        verify(debitCardRepository, times(1)).findByDebitCardNumber(debitCardNumber);
    }
    @Test
    public void testAccountBalance_InvalidPinNumber(){
        String debitCardNumber = "9876543210987654";
        int pin = 5431;
        long amount = 1000;
        DebitCard debitCard = new DebitCard();
        debitCard.setDebitCardNumber(debitCardNumber);
        debitCard.setPin(1234);
        when(debitCardRepository.findByDebitCardNumber(debitCardNumber)).thenReturn(debitCard);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            cashDepositService.depositCash(debitCardNumber,pin,amount);
        });
        assertNotNull(exception);
        assertEquals("Please enter valid pin number/card number",exception.getMessage());
    }



}
