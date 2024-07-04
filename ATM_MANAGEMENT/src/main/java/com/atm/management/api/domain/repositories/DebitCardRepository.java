package com.atm.management.api.domain.repositories;


import com.atm.management.api.domain.entities.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard,Integer> {

    @Query("select d from DebitCard d where d.DebitCardNumber=:debitCardNumber")
    DebitCard findByDebitCardNumber(@Param("debitCardNumber") String debitCardNumber);
}
