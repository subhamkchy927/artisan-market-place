package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.AddressRequestDto;
import com.artisan_market_place.responseDto.AddressResponseDto;
import com.artisan_market_place.serviceImpl.AddressServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v2/address")
@Slf4j
public class AddressController {
    private final AddressServiceImpl addressService;
    private final JwtUtil jwtUtil;

    public AddressController(AddressServiceImpl addressService, JwtUtil jwtUtil) {
        this.addressService = addressService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto dto,
                                                            @RequestHeader(value = "Authorization") String token) {
        log.info("Request to create address",dto);
        String loginUser = jwtUtil.extractUsername(token);
        AddressResponseDto response = addressService.createAddress(dto,loginUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(@RequestBody AddressRequestDto dto,
                                                            @PathVariable Long addressId,
                                                            @RequestHeader(value = "Authorization") String token) {
        log.info("Request to update address",dto);
        log.info("Request to update address with ID: {}", addressId);
        String loginUser = jwtUtil.extractUsername(token);
        AddressResponseDto response = addressService.updateAddress(dto, addressId, loginUser);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable Long addressId,
                                                             @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching address with ID: {}", addressId);
        String loginUser = jwtUtil.extractUsername(token);
        AddressResponseDto response = addressService.getAddressById(addressId, loginUser);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<HashMap<String, String>> deleteAddress(@PathVariable Long addressId,
                                                                 @RequestHeader(value = "Authorization") String token) {
        log.info("Request to delete address with ID: {}", addressId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String, String> response = addressService.deleteAddress(addressId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses(@PathVariable Long userId, @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching all addresses");
        String loginUser = jwtUtil.extractUsername(token);
        List<AddressResponseDto> addresses = addressService.getAllAddresses(userId);
        return ResponseEntity.ok(addresses);
    }
}
