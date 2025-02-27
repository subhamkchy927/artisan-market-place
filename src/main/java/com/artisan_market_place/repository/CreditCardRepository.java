package com.artisan_market_place.repository;

import com.artisan_market_place.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
    List<CreditCard> findByUserId(Long userId);
}
