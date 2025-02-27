package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.CreditCardRequestDto;
import com.artisan_market_place.responseDto.CreditCardResponseDto;
import com.artisan_market_place.service.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/credit-card")
public class CreditCardController{
    private final CreditCardService creditCardService;
    private final JwtUtil jwtUtil;
    public CreditCardController(CreditCardService creditCardService, JwtUtil jwtUtil) {
        this.creditCardService = creditCardService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public ResponseEntity<CreditCardResponseDto> addCreditCard(
            @RequestBody CreditCardRequestDto dto,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(creditCardService.addCreditCard(dto, loginUser));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CreditCardResponseDto> updateCreditCard(
            @RequestBody CreditCardRequestDto dto,
            @PathVariable Long cardId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(creditCardService.updateCreditCard(dto, cardId, loginUser));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CreditCardResponseDto> getCreditCardById(
            @PathVariable Long cardId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(creditCardService.getCreditCardById(cardId, loginUser));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardResponseDto>> getAllCreditCards(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(creditCardService.getAllCreditCards(userId, loginUser));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Map<String, String>> deleteCreditCard(
            @PathVariable Long cardId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(creditCardService.deleteCreditCard(cardId, loginUser));
    }
}

