package com.atm.management.api.domain.services;
import com.atm.management.api.domain.dtos.CashWithdrawalResponseDto;
import com.atm.management.api.domain.entities.Atm;
import com.atm.management.api.domain.entities.DebitCard;
import com.atm.management.api.domain.exceptions.TransactionException;
import com.atm.management.api.domain.exceptions.ValidationException;
import com.atm.management.api.domain.repositories.AtmRepository;
import com.atm.management.api.domain.repositories.DebitCardRepository;
import com.atm.management.api.domain.repositories.TransactionDetailsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CashWithdrawalServiceTest {

    @InjectMocks
    CashWithdrawalServiceTest cashWithdrawalService;

    @Mock
    DebitCardRepository debitCardRepository;

    @Mock
    AtmRepository atmRepository;

    @Mock
    TransactionDetailsRepository transactionDetailsRepository;


}

