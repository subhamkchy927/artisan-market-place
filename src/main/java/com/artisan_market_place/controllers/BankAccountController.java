package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.BankAccountRequestDto;
import com.artisan_market_place.responseDto.BankAccountResponseDto;
import com.artisan_market_place.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/bank-account")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final JwtUtil jwtUtil;

    public BankAccountController(BankAccountService bankAccountService, JwtUtil jwtUtil) {
        this.bankAccountService = bankAccountService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping
    public ResponseEntity<BankAccountResponseDto> addBankAccount(
            @RequestBody BankAccountRequestDto dto,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(bankAccountService.addBankAccount(dto, loginUser));
    }
    @PutMapping("/{bankAccountId}")
    public ResponseEntity<BankAccountResponseDto> updateBankAccount(
            @RequestBody BankAccountRequestDto dto,
            @PathVariable Long bankAccountId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(bankAccountService.updateBankAccount(dto, bankAccountId, loginUser));
    }
    @GetMapping("/{bankAccountId}")
    public ResponseEntity<BankAccountResponseDto> getBankAccountById(
            @PathVariable Long bankAccountId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(bankAccountService.getBankAccountById(bankAccountId, loginUser));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccountResponseDto>> getAllBankAccounts(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts(userId, loginUser));
    }
    @DeleteMapping("/{bankAccountId}")
    public ResponseEntity<Map<String, String>> deleteBankAccount(
            @PathVariable Long bankAccountId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(bankAccountService.deleteBankAccount(bankAccountId, loginUser));
    }
}