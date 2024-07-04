package com.atm.management.api.domain.services;

import com.atm.management.api.domain.dtos.CashDepositResponseDto;
import com.atm.management.api.domain.entities.Atm;
import com.atm.management.api.domain.entities.DebitCard;
import com.atm.management.api.domain.entities.TransactionDetails;
import com.atm.management.api.domain.enums.CustomHttpStatus;
import com.atm.management.api.domain.exceptions.ValidationException;
import com.atm.management.api.domain.repositories.AtmRepository;
import com.atm.management.api.domain.repositories.DebitCardRepository;
import com.atm.management.api.domain.repositories.TransactionDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@Slf4j
public class CashDepositService {

    @Autowired
    DebitCardRepository debitCardRepository;

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    AtmRepository atmRepository;

    public CashDepositResponseDto depositCash(String debitCardNumber, int pin, long amount) {
        BalanceEnquiryService. validateDebitCardNumberAndPin(debitCardNumber,pin);
        DebitCard debitCardDetails = debitCardRepository.findByDebitCardNumber(debitCardNumber);
        if(debitCardDetails==null || debitCardDetails.getPin()!=pin){
            log.info("Method name:getAccountBalance Wrong debitCardNumber : {} or pin : {}",debitCardNumber,pin);
            throw new ValidationException("Please enter valid pin number/card number", CustomHttpStatus.BAD_REQUEST);
        }
        long cashAvailable = debitCardDetails.getAccountBalance();
        CashDepositResponseDto responseDto = new CashDepositResponseDto();

        transactionSuccess(responseDto, amount, cashAvailable);
        updateDebitCardDetails(debitCardDetails,cashAvailable,amount);
        updateTransactionDetails(debitCardDetails, amount);
        Atm atm = atmRepository.getCashAvailable();
        updateAtm(atm,amount);

        return responseDto;
    }
    private void transactionSuccess(CashDepositResponseDto responseDto, long amount, long cashAvailable) {
        responseDto.setMessage("Cash deposited Successfully..!");
        responseDto.setAvailableBalance(cashAvailable + amount);
    }
    private void updateDebitCardDetails(DebitCard debitCardDetails, long cashAvailable, long amount) {
        debitCardDetails.setAccountBalance(cashAvailable + amount);
        debitCardRepository.save(debitCardDetails);
    }
    private void updateAtm(Atm atm, long amount) {
        atm.setCashAvailable(atm.getCashAvailable() + amount);
        atmRepository.save(atm);
    }
    private void updateTransactionDetails(DebitCard debitCardDetails, long amount) {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setAmount(amount);
        transactionDetails.setTransactionType("deposit");

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String formattedDateTime = dateTime.format(formatter);

        transactionDetails.setTransactionDate(formattedDateTime);
        transactionDetails.setDebitCardNumber(debitCardDetails.getDebitCardNumber());
        transactionDetails.setCustomerId(debitCardDetails.getCustomerId());
        transactionDetails.setAccountNumber(debitCardDetails.getAccountNumber());
        transactionDetailsRepository.save(transactionDetails);
    }
}
