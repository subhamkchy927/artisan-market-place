package com.artisan_market_place.repository;

import com.artisan_market_place.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<CreditCard,Long> {
    //
}
