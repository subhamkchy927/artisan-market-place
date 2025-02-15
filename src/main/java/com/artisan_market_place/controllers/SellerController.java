package com.artisan_market_place.controllers;

import com.artisan_market_place.requestDto.SellerRequestDto;
import com.artisan_market_place.responseDto.SellerResponseDto;
import com.artisan_market_place.serviceImpl.SellerDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/seller")
@Slf4j
public class SellerController {
    private final SellerDetailsServiceImpl sellerService;
    public SellerController(SellerDetailsServiceImpl sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/")
        public ResponseEntity<SellerResponseDto> createSeller(@RequestBody SellerRequestDto dto) {
            log.info("Request from client for seller creation"+dto);
            SellerResponseDto response = sellerService.createSeller(dto);
            return ResponseEntity.ok(response);
        }

    @PutMapping("/{sellerId}")
    public ResponseEntity<SellerResponseDto> updateSeller(
            @RequestBody SellerRequestDto dto,
            @PathVariable Long sellerId) {
        log.info("Request to update seller with ID: {}", sellerId);
        SellerResponseDto response = sellerService.updateSeller(dto, sellerId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<SellerResponseDto> getSellerById(@PathVariable Long sellerId) {
        log.info("Fetching seller with ID: {}", sellerId);
        SellerResponseDto response = sellerService.getSellerById(sellerId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{sellerId}")
    public ResponseEntity<SellerResponseDto> deleteSeller(@PathVariable Long sellerId) {
        log.info("Request to delete seller with ID: {}", sellerId);
        SellerResponseDto response = sellerService.deleteSeller(sellerId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<SellerResponseDto>> getAllSellers(
            @RequestParam(required = false) Boolean isApplicationAdmin) {
        log.info("Fetching all sellers. isApplicationAdmin: {}", isApplicationAdmin);
        List<SellerResponseDto> sellers = sellerService.getAllSeller(isApplicationAdmin);
        return ResponseEntity.ok(sellers);
    }
    }

