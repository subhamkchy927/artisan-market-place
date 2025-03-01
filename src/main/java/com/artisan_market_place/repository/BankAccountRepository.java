package com.artisan_market_place.repository;

import com.artisan_market_place.entity.BankAccount;
import com.artisan_market_place.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long>{
    List<BankAccount> findByUserId(Long userId);
    boolean existsByAccountNumberAndUserId(String accountNumber, Long userId);
    boolean existsByAccountNumberAndUserIdAndBankAccountIdNot(String accountNumber, Long userId, Long bankAccountId);
}
