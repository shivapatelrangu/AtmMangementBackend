package com.atm.management.api.domain.services;

import com.atm.management.api.domain.dtos.BalanceEnqueryResponseDto;
import com.atm.management.api.domain.entities.DebitCard;
import com.atm.management.api.domain.enums.CustomHttpStatus;
import com.atm.management.api.domain.exceptions.ValidationException;
import com.atm.management.api.domain.repositories.CustomerRepository;
import com.atm.management.api.domain.repositories.DebitCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BalanceEnquiryService {


    @Autowired
    DebitCardRepository debitCardRepository;

    @Autowired
    CustomerRepository customerRepository;

    public BalanceEnqueryResponseDto getAccountBalance(String debitCardNumber, int pin) {

        validateDebitCardNumberAndPin(debitCardNumber,pin);

        DebitCard debitCardDetails = debitCardRepository.findByDebitCardNumber(debitCardNumber);
        if(debitCardDetails==null || debitCardDetails.getPin()!=pin){
            log.info("Method name:getAccountBalance Wrong debitCardNumber : {} or pin : {}",debitCardNumber,pin);
            throw new ValidationException("Please enter valid pin number/card number",CustomHttpStatus.BAD_REQUEST);
        }
        BalanceEnqueryResponseDto response = new BalanceEnqueryResponseDto();
        balanceEnquirySuccess(debitCardDetails,response);
        return response;
    }

    public void balanceEnquirySuccess(DebitCard debitCardDetails, BalanceEnqueryResponseDto response) {
        log.info("Method name:balanceEnquirySuccess requested from getAccountBalance method");
        response.setAccountBalance(debitCardDetails.getAccountBalance());

    }

    static void validateDebitCardNumberAndPin(String debitCardNumber, int pin) {
        String pinNumber = String.valueOf(pin);
        if(debitCardNumber.isEmpty()){
            log.info("Method name:validateDebitCardNumberAndPin Wrong debit card number {}",debitCardNumber);
            throw new ValidationException("Debit card number should not be empty",CustomHttpStatus.BAD_REQUEST);
        }

        if(debitCardNumber.length() != 16){
            log.info("Method name:validateDebitCardNumberAndPin Wrong debit card number {}",debitCardNumber);
            throw new ValidationException("Debit card number length should be equal to 16 digits",CustomHttpStatus.BAD_REQUEST);
        }

        if(pinNumber.length()!=4){
            log.info("Method name:validateDebitCardNumberAndPin wrong pin number {}",pin);
            throw new ValidationException("Pin number length should be equal to 4 digits",CustomHttpStatus.BAD_REQUEST);
        }
    }
}
