package com.atm.management.api.domain.repositories;

import com.atm.management.api.domain.entities.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmRepository extends JpaRepository<Atm,Integer> {

    @Query("select a from Atm a ")
    Atm getCashAvailable();
}
