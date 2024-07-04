package com.atm.management.api.domain.services;

import com.atm.management.api.domain.dtos.BalanceEnqueryResponseDto;
import com.atm.management.api.domain.entities.DebitCard;
import com.atm.management.api.domain.exceptions.ValidationException;
import com.atm.management.api.domain.repositories.CustomerRepository;
import com.atm.management.api.domain.repositories.DebitCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BalanceEnquiryServiceTest {

    @InjectMocks
    BalanceEnquiryService balanceEnquiryService;

    @Mock
    DebitCardRepository debitCardRepository;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void testBalanceEnquiry_ValidCardAndPin(){
        String debitCardNumber = "3333222211110000";
        int pin = 1234;
        DebitCard debitCard = new DebitCard();
        debitCard.setAccountBalance(10000);
        debitCard.setPin(pin);
        when(debitCardRepository.findByDebitCardNumber(debitCardNumber)).thenReturn(debitCard);
        BalanceEnqueryResponseDto responseDto = balanceEnquiryService.getAccountBalance(debitCardNumber,pin);
        assertNotNull(responseDto);
        assertEquals(10000,responseDto.getAccountBalance());
        verify(debitCardRepository, times(1)).findByDebitCardNumber(debitCardNumber);
    }
    @Test
    public void testAccountBalance_InvalidCardNumber(){
        String debitCardNumber = "9876543210987652";
        int pin = 5432;
        DebitCard debitCard = new DebitCard();
        debitCard.setDebitCardNumber(debitCardNumber);
        debitCard.setPin(1234);
        when(debitCardRepository.findByDebitCardNumber(debitCardNumber)).thenReturn(debitCard);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            balanceEnquiryService.getAccountBalance(debitCardNumber,pin);
        });
        assertEquals("Please enter valid pin number/card number",exception.getMessage());
        verify(debitCardRepository, times(1)).findByDebitCardNumber(debitCardNumber);
    }
    @Test
    public void testAccountBalance_InvalidPinNumber(){
        String debitCardNumber = "9876543210987654";
        int pin = 5431;
        DebitCard debitCard = new DebitCard();
        debitCard.setDebitCardNumber(debitCardNumber);
        debitCard.setPin(1234);
        when(debitCardRepository.findByDebitCardNumber(debitCardNumber)).thenReturn(debitCard);
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            balanceEnquiryService.getAccountBalance(debitCardNumber,pin);
        });
        assertNotNull(exception);
        assertEquals("Please enter valid pin number/card number",exception.getMessage());
    }
    @Test
    public void testWhenDebitCardNumberIsEmpty(){
        String debitCardNumber = "";
        int pin = 5432;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            balanceEnquiryService.validateDebitCardNumberAndPin(debitCardNumber,pin);
        });
        assertNotNull(exception);
        assertEquals("Debit card number should not be empty",exception.getMessage());
        verify(debitCardRepository, never()).findByDebitCardNumber(anyString());
    }
    @Test
    public void testWhenDebitCardNumberLengthIsIncorrect(){
        String debitCardNumber = "98765432109876";
        int pin = 5431;

        ValidationException exception = assertThrows(ValidationException.class, ()->{
            balanceEnquiryService.validateDebitCardNumberAndPin(debitCardNumber,pin);
        });
        assertNotNull(exception);
        assertEquals("Debit card number length should be equal to 16 digits",exception.getMessage());
    }
    @Test
    public void testWhenPinNumberLengthIsIncorrect(){
        String debitCardNumber = "9876543210987654";
        int pin = 541;

        ValidationException exception = assertThrows(ValidationException.class, ()->{
            balanceEnquiryService.validateDebitCardNumberAndPin(debitCardNumber,pin);
        });
        assertNotNull(exception);
        assertEquals("Pin number length should be equal to 4 digits",exception.getMessage());
    }
    @Test
    void testBalanceEnquirySuccess() {

        DebitCard debitCard = new DebitCard();
        debitCard.setAccountBalance(1000);
        BalanceEnqueryResponseDto response = new BalanceEnqueryResponseDto();

        balanceEnquiryService.balanceEnquirySuccess(debitCard, response);
        assertEquals(1000, response.getAccountBalance());
    }

}
