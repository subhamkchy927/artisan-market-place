package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.ProductOfferRequestDto;
import com.artisan_market_place.responseDto.ProductOfferResponseDto;
import com.artisan_market_place.serviceImpl.ProductOfferServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v2/offer")
@Slf4j
public class ProductOfferController {

    private final ProductOfferServiceImpl productOfferService;
    private final JwtUtil jwtUtil;

    public ProductOfferController(ProductOfferServiceImpl productOfferService, JwtUtil jwtUtil) {
        this.productOfferService = productOfferService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public ResponseEntity<ProductOfferResponseDto> createOffer(
            @RequestBody ProductOfferRequestDto dto,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Creating offer: {}", dto);
        String loginUser = jwtUtil.extractUsername(token);
        ProductOfferResponseDto response = productOfferService.createOffer(dto, loginUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<ProductOfferResponseDto> updateOffer(
            @RequestBody ProductOfferRequestDto dto,
            @PathVariable Long offerId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Updating offer with ID: {}", offerId);
        String loginUser = jwtUtil.extractUsername(token);
        ProductOfferResponseDto response = productOfferService.updateOffer(dto, offerId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<ProductOfferResponseDto> getOfferById(
            @PathVariable Long offerId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching offer with ID: {}", offerId);
        String loginUser = jwtUtil.extractUsername(token);
        ProductOfferResponseDto response = productOfferService.getOfferById(offerId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductOfferResponseDto>> getAllOffers(
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching all offers");
        String loginUser = jwtUtil.extractUsername(token);
        List<ProductOfferResponseDto> response = productOfferService.getAllOffers(loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductOfferResponseDto>> getOffersByProductId(
            @PathVariable Long productId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching offers for product ID: {}", productId);
        String loginUser = jwtUtil.extractUsername(token);
        List<ProductOfferResponseDto> response = productOfferService.getOffersByProductId(productId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductOfferResponseDto>> getActiveOffers(
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching active offers");
        String loginUser = jwtUtil.extractUsername(token);
        List<ProductOfferResponseDto> response = productOfferService.getActiveOffers(loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active/product/{productId}")
    public ResponseEntity<List<ProductOfferResponseDto>> getActiveOffersByProductId(
            @PathVariable Long productId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching active offers for product ID: {}", productId);
        String loginUser = jwtUtil.extractUsername(token);
        List<ProductOfferResponseDto> response = productOfferService.getActiveOffersByProductId(productId, loginUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<HashMap<String, String>> deleteOffer(
            @PathVariable Long offerId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Deleting offer with ID: {}", offerId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String, String> response = productOfferService.deleteOffer(offerId, loginUser);
        return ResponseEntity.ok(response);
    }
}

