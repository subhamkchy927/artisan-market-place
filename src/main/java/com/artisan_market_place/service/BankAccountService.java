package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.BankAccountRequestDto;
import com.artisan_market_place.responseDto.BankAccountResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BankAccountService {
    BankAccountResponseDto addBankAccount(BankAccountRequestDto dto, String loginUser);
    BankAccountResponseDto updateBankAccount(BankAccountRequestDto dto, Long bankAccountId, String loginUser);
    BankAccountResponseDto getBankAccountById(Long bankAccountId, String loginUser);
    List<BankAccountResponseDto> getAllBankAccounts(Long userId, String loginUser);
    Map<String, String> deleteBankAccount(Long bankAccountId, String loginUser);
}
