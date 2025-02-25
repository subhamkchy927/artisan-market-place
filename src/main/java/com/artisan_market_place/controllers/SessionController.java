package com.artisan_market_place.controllers;

import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/session/")
public class SessionController {

    private final UserServiceImpl userService;

    public SessionController(UserServiceImpl sellerService) {
        this.userService = sellerService;
    }

    @PostMapping("/register/")
    public ResponseEntity<UserResponseDto> updateSeller(
            @RequestBody UserRequestDto dto) {
        log.info("Request to create user with ID: {}", dto);
        UserResponseDto response = userService.createUser(dto);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }



}
