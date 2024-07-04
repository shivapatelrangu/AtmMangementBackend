package com.atm.management.api.domain.repositories;


import com.atm.management.api.domain.entities.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Integer> {
    @Query("select t from TransactionDetails t where t.debitCardNumber=:debitCardNumber")
    List<TransactionDetails> findByCardNumberAndAccountNumber(@Param("debitCardNumber") String debitCardNumber);

    @Query("select count(t) from TransactionDetails t where (t.debitCardNumber=:debitCardNumber AND t.transactionType=:transactionType) AND t.transactionDate BETWEEN  :yesterdayDateTime AND :todayDateTime ")
    int countNoOfTransactions(@Param("debitCardNumber") String debitCardNumber, @Param("transactionType") String transactionType, @Param("todayDateTime") String todayDateTime, @Param("yesterdayDateTime") String yesterdayDateTime);

    @Query("select COALESCE(SUM(t.amount), 0) AS total_amount from TransactionDetails t where (t.debitCardNumber=:debitCardNumber AND t.transactionType=:transactionType) AND t.transactionDate BETWEEN  :yesterdayDateTime AND :todayDateTime ")
    long findtransactionAmount(@Param("debitCardNumber") String debitCardNumber, @Param("transactionType") String transactionType, @Param("todayDateTime") String todayDateTime, @Param("yesterdayDateTime") String yesterdayDateTime);
}
