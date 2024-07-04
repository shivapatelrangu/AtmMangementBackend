package com.atm.management.api.domain.controllers;

import com.atm.management.api.domain.dtos.CashDepositResponseDto;
import com.atm.management.api.domain.services.CashDepositService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api-deposit")
public class CashDepositController {

    @Autowired
    CashDepositService cashDepositService;

        @PostMapping("/cash-deposit")
    public ResponseEntity<CashDepositResponseDto> depositCash(@RequestParam("debitCardNumber") String debitCardNumber, @RequestParam("pin") int pin, @RequestParam("amount") long amount){
        log.info("API Endpoint /api-deposit/cash-deposit Method : depositCash, debitCardNumber {} pin {}",debitCardNumber,pin);
        CashDepositResponseDto response = cashDepositService.depositCash(debitCardNumber,pin,amount);
        return ResponseEntity.ok(response);
    }

}
