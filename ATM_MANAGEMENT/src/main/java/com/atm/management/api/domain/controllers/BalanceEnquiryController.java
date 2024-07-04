package com.atm.management.api.domain.controllers;

import com.atm.management.api.domain.dtos.BalanceEnqueryResponseDto;
import com.atm.management.api.domain.services.BalanceEnquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-enquiry")
@Slf4j
public class BalanceEnquiryController {

    @Autowired
    BalanceEnquiryService balanceEnquiryService;

    @GetMapping ("/balance-enquiry")
    public ResponseEntity<BalanceEnqueryResponseDto> getAccountBalance(@RequestParam("debitCardNumber") String debitCardNumber, @RequestParam("pin") int pin){
        log.info("API Endpoint /api-enquiry/balance-enquiry DebitCardNumber {} Pin {}",debitCardNumber,pin);
        BalanceEnqueryResponseDto response = balanceEnquiryService.getAccountBalance(debitCardNumber,pin);
        return ResponseEntity.ok(response);
    }
}
