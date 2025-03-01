package com.artisan_market_place.controllers;

import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.serviceImpl.ApplicationDetailsServiceImpl;
import com.artisan_market_place.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/session/")
public class SessionController {

    private final UserServiceImpl userService;
    private final ApplicationDetailsServiceImpl applicationDetailsServiceImpl;

    public SessionController(UserServiceImpl sellerService, ApplicationDetailsServiceImpl applicationDetailsServiceImpl) {
        this.userService = sellerService;
        this.applicationDetailsServiceImpl = applicationDetailsServiceImpl;
    }

    @PostMapping("/register/")
    public ResponseEntity<UserResponseDto> updateSeller(
            @RequestBody UserRequestDto dto) {
        log.info("Request to create user", dto);
        UserResponseDto response = userService.createUser(dto);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/application-details/")
    public ResponseEntity<String> getApplicationDetails() {
        String details  = applicationDetailsServiceImpl.getApplicationDetails();
        return details != null ? ResponseEntity.ok(details) : ResponseEntity.notFound().build();
    }



}
