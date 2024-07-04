package com.atm.management.api.domain.controllers;


import com.atm.management.api.domain.dtos.CashWithdrawalResponseDto;
import com.atm.management.api.domain.exceptions.TransactionException;
import com.atm.management.api.domain.services.CashWithdrawalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/withdrawal-api")
public class CashWithdrawalController {

    @Autowired
    CashWithdrawalService cashWithdrawalService;

    @GetMapping ("/cash-withdrawal")
    public ResponseEntity<CashWithdrawalResponseDto> getCash(@RequestParam("debitCardNumber") String debitCardNumber, @RequestParam("pin") int pin, @RequestParam("amount") long amount) throws TransactionException {
        log.info("API Endpoint : /withdrawal-api/cash-withdrawal, Method : getCash, debitCardNumber {} pin {}",debitCardNumber,pin);
        CashWithdrawalResponseDto response = cashWithdrawalService.getCash(debitCardNumber,pin,amount);
        return ResponseEntity.ok(response);
    }
}
