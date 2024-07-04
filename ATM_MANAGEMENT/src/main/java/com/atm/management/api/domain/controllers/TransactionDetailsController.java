package com.atm.management.api.domain.controllers;

import com.atm.management.api.domain.dtos.TransactionHistoryResponseDto;
import com.atm.management.api.domain.services.TransactionDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api-history")
public class TransactionDetailsController {

    @Autowired
    TransactionDetailsService transactionDetailsService;

    @GetMapping("/transaction-details")
    public ResponseEntity<List<TransactionHistoryResponseDto>> getTransactionHistory(@RequestParam("debitCardNumber") String debitCardNumber, @RequestParam("pin") int pin){
        log.info("API Endpoint /api-history/transaction-details Method:getTransactionHistory debitCardNumber {} , pin {}",debitCardNumber,pin);
        List<TransactionHistoryResponseDto> response = transactionDetailsService.getTransactionHistory(debitCardNumber,pin);
        return ResponseEntity.ok(response);
    }

}
