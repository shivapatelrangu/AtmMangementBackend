package com.atm.management.api.domain.services;

import com.atm.management.api.domain.dtos.TransactionHistoryResponseDto;
import com.atm.management.api.domain.entities.DebitCard;
import com.atm.management.api.domain.entities.TransactionDetails;
import com.atm.management.api.domain.enums.CustomHttpStatus;
import com.atm.management.api.domain.exceptions.ValidationException;
import com.atm.management.api.domain.repositories.DebitCardRepository;
import com.atm.management.api.domain.repositories.TransactionDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransactionDetailsService {

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    DebitCardRepository debitCardRepository;

    public List<TransactionHistoryResponseDto> getTransactionHistory(String debitCardNumber, int pin) {
        BalanceEnquiryService. validateDebitCardNumberAndPin(debitCardNumber,pin);

        DebitCard debitCardDetails = debitCardRepository.findByDebitCardNumber(debitCardNumber);
        if(debitCardDetails==null || debitCardDetails.getPin()!=pin){
            log.info("Method name:getAccountBalance Wrong debitCardNumber : {} or pin : {}",debitCardNumber,pin);
            throw new ValidationException("Enter valid pin number/card number", CustomHttpStatus.BAD_REQUEST);
        }
        List<TransactionDetails> response = transactionDetailsRepository.findByCardNumberAndAccountNumber(debitCardNumber);
        if(response.isEmpty()){
            throw new ValidationException("There are no transactions associated with the specified debit card number.",CustomHttpStatus.BAD_REQUEST);
        }
        List<TransactionHistoryResponseDto> result = new ArrayList<>();
        for (TransactionDetails transactionDetails : response) {
            TransactionHistoryResponseDto res = new TransactionHistoryResponseDto();
            res.setTransactionId(transactionDetails.getTransactionId());
            res.setAccountNumber(transactionDetails.getAccountNumber());
            res.setDebitCardNumber(transactionDetails.getDebitCardNumber());
            res.setAmount(transactionDetails.getAmount());
            res.setTransactionType(transactionDetails.getTransactionType());
            res.setTransactionDate(transactionDetails.getTransactionDate());
            result.add(res);
        }
        return result;
    }

}
//String dtoListString = response.stream()
//        .map(TransactionDetails::toString)
//        .collect(Collectors.joining(", "));
