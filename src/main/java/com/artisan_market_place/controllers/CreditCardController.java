package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.CreditCardRequestDto;
import com.artisan_market_place.responseDto.CreditCardResponseDto;
import com.artisan_market_place.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/credit-card")
@Slf4j
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
        log.info("Adding credit card for user: {}", loginUser);
        return ResponseEntity.ok(creditCardService.addCreditCard(dto, loginUser));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CreditCardResponseDto> updateCreditCard(
            @RequestBody CreditCardRequestDto dto,
            @PathVariable Long cardId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Updating credit card with ID: {} for user: {}", cardId, loginUser);
        return ResponseEntity.ok(creditCardService.updateCreditCard(dto, cardId, loginUser));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CreditCardResponseDto> getCreditCardById(
            @PathVariable Long cardId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Fetching credit card with ID: {} for user: {}", cardId, loginUser);
        return ResponseEntity.ok(creditCardService.getCreditCardById(cardId, loginUser));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardResponseDto>> getAllCreditCards(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Fetching all credit cards for user ID: {} by requester: {}", userId, loginUser);
        return ResponseEntity.ok(creditCardService.getAllCreditCards(userId, loginUser));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Map<String, String>> deleteCreditCard(
            @PathVariable Long cardId,
            @RequestHeader("Authorization") String token) {
        String loginUser = jwtUtil.extractUsername(token);
        log.info("Deleting credit card with ID: {} for user: {}", cardId, loginUser);
        return ResponseEntity.ok(creditCardService.deleteCreditCard(cardId, loginUser));
    }
}
