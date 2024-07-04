package com.atm.management.api.domain.services;

import com.atm.management.api.domain.dtos.CashWithdrawalResponseDto;
import com.atm.management.api.domain.entities.Atm;
import com.atm.management.api.domain.entities.DebitCard;
import com.atm.management.api.domain.entities.TransactionDetails;
import com.atm.management.api.domain.enums.CustomHttpStatus;
import com.atm.management.api.domain.exceptions.TransactionException;
import com.atm.management.api.domain.exceptions.ValidationException;
import com.atm.management.api.domain.repositories.AtmRepository;
import com.atm.management.api.domain.repositories.DebitCardRepository;
import com.atm.management.api.domain.repositories.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class CashWithdrawalService {
    @Autowired
    DebitCardRepository debitCardRepository;

    @Autowired
    AtmRepository atmRepository;

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    public CashWithdrawalResponseDto getCash(String debitCardNumber, int pin, long amount) throws TransactionException {

        BalanceEnquiryService. validateDebitCardNumberAndPin(debitCardNumber,pin);

        DebitCard debitCardDetails = debitCardRepository.findByDebitCardNumber(debitCardNumber);
        Atm atm = atmRepository.getCashAvailable();

        validateCardDetails(debitCardDetails,debitCardNumber,amount,pin,atm);

        CashWithdrawalResponseDto responseDto = new CashWithdrawalResponseDto();

        transactionSuccess(debitCardDetails, amount, responseDto);
        updateAtmCash(atm,amount);
        updateDebitCardDetails(debitCardDetails, amount);
        updateTransactionDetails(debitCardDetails, amount);

        return responseDto;
    }

    private void transactionSuccess(DebitCard debitCardDetails, long amount, CashWithdrawalResponseDto responseDto) {
        responseDto.setMesaage("Transaction Successfull");
        responseDto.setAvailableBalance(debitCardDetails.getAccountBalance()-amount);
    }
    private void updateAtmCash(Atm atm, long amount) {
        atm.setCashAvailable(atm.getCashAvailable()-amount);
        atmRepository.save(atm);
    }
    private void updateDebitCardDetails(DebitCard debitCardDetails, long amount) {
        debitCardDetails.setAccountBalance(debitCardDetails.getAccountBalance()-amount);
        debitCardRepository.save(debitCardDetails);
    }
    private void updateTransactionDetails(DebitCard debitCardDetails, long amount) {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setAmount(amount);
        transactionDetails.setTransactionType("withdrawal");

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String formattedDateTime = dateTime.format(formatter);

        transactionDetails.setTransactionDate(formattedDateTime);
        transactionDetails.setDebitCardNumber(debitCardDetails.getDebitCardNumber());
        transactionDetails.setCustomerId(debitCardDetails.getCustomerId());
        transactionDetails.setAccountNumber(debitCardDetails.getAccountNumber());

        transactionDetailsRepository.save(transactionDetails);
    }

    private void validateCardDetails(DebitCard debitCardDetails, String debitCardNumber, long amount, int pin, Atm atm) throws TransactionException {
        if(debitCardDetails==null || debitCardDetails.getPin()!=pin){
                throw new ValidationException("Please enter valid pin or card number", CustomHttpStatus.BAD_REQUEST);
        }
        if(amount<=0){
            throw new ValidationException("The transaction amount must be greater than zero",CustomHttpStatus.BAD_REQUEST);
        }
        if(amount>10000){
            throw new ValidationException("A maximum withdrawal limit of INR 10,000 is permitted per transaction." ,CustomHttpStatus.BAD_REQUEST);
        }

        if(amount>atm.getCashAvailable()){
            throw new ValidationException("Required amount is not available",CustomHttpStatus.BAD_REQUEST);
        }
        if(amount>debitCardDetails.getAccountBalance()){
            throw new ValidationException("Your account balance is "+debitCardDetails.getAccountBalance(),CustomHttpStatus.BAD_REQUEST);
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todayDateTime = now.format(formatter);
        String yesterdayDateTime = yesterday.format(formatter);

        String transactionType = "withdrawal";

        int noOfTransactions = transactionDetailsRepository.countNoOfTransactions(debitCardNumber,transactionType,todayDateTime,yesterdayDateTime);
        if(noOfTransactions >= 5){
            throw new ValidationException("The maximum allowed number of daily transactions is limited to 5. Please try again tomorrow",CustomHttpStatus.BAD_REQUEST);
        }

        long transactionAmount = transactionDetailsRepository.findtransactionAmount(debitCardNumber,transactionType,todayDateTime,yesterdayDateTime);
        if(transactionAmount > 20000 || transactionAmount+amount > 20000){
            throw new TransactionException("The transaction limit has been exceeded.");
        }

    }
}
