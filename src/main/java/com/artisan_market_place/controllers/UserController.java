package com.artisan_market_place.controllers;

import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v2/user")
@Slf4j
public class UserController {
    private final UserServiceImpl sellerService;
    public UserController(UserServiceImpl sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDto> createSeller(@RequestBody UserRequestDto dto) {
        log.info("Request from client for seller creation"+dto);
        UserResponseDto response = sellerService.createSeller(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{sellerId}")
    public ResponseEntity<UserResponseDto> updateSeller(
            @RequestBody UserRequestDto dto,
            @PathVariable Long sellerId) {
        log.info("Request to update seller with ID: {}", sellerId);
        UserResponseDto response = sellerService.updateSeller(dto, sellerId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<UserResponseDto> getSellerById(@PathVariable Long sellerId) {
        log.info("Fetching seller with ID: {}", sellerId);
        UserResponseDto response = sellerService.getSellerById(sellerId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{sellerId}")
    public ResponseEntity<HashMap<String,String>> deleteSeller(@PathVariable Long sellerId) {
        log.info("Request to delete seller with ID: {}", sellerId);
        HashMap<String,String> response = sellerService.deleteSeller(sellerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAllSellers(
            @RequestParam(required = false) Boolean isApplicationAdmin) {
        log.info("Fetching all sellers. isApplicationAdmin: {}", isApplicationAdmin);
        List<UserResponseDto> sellers = sellerService.getAllSeller(isApplicationAdmin);
        return ResponseEntity.ok(sellers);
    }
    }

