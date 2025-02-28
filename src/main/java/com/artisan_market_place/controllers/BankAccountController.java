package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.BankAccountRequestDto;
import com.artisan_market_place.responseDto.BankAccountResponseDto;
import com.artisan_market_place.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/bank-account")
@Slf4j
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final JwtUtil jwtUtil;

    public BankAccountController(BankAccountService bankAccountService, JwtUtil jwtUtil) {
        this.bankAccountService = bankAccountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public ResponseEntity<BankAccountResponseDto> addBankAccount(
            @RequestBody BankAccountRequestDto dto,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Request to add a new bank account by user: {}", loginUser);
        return ResponseEntity.ok(bankAccountService.addBankAccount(dto, loginUser));
    }

    @PutMapping("/{bankAccountId}")
    public ResponseEntity<BankAccountResponseDto> updateBankAccount(
            @RequestBody BankAccountRequestDto dto,
            @PathVariable Long bankAccountId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Request to update bank account ID: {} by user: {}", bankAccountId, loginUser);
        return ResponseEntity.ok(bankAccountService.updateBankAccount(dto, bankAccountId, loginUser));
    }

    @GetMapping("/{bankAccountId}")
    public ResponseEntity<BankAccountResponseDto> getBankAccountById(
            @PathVariable Long bankAccountId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Fetching bank account details for ID: {} by user: {}", bankAccountId, loginUser);
        return ResponseEntity.ok(bankAccountService.getBankAccountById(bankAccountId, loginUser));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccountResponseDto>> getAllBankAccounts(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Fetching all bank accounts for user ID: {} requested by: {}", userId, loginUser);
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts(userId, loginUser));
    }

    @DeleteMapping("/{bankAccountId}")
    public ResponseEntity<Map<String, String>> deleteBankAccount(
            @PathVariable Long bankAccountId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Request to delete bank account ID: {} by user: {}", bankAccountId, loginUser);
        return ResponseEntity.ok(bankAccountService.deleteBankAccount(bankAccountId, loginUser));
    }
}