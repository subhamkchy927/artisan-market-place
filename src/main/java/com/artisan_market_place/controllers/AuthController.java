package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.AuthService;
import com.artisan_market_place.requestDto.LoginRequestDto;
import com.artisan_market_place.responseDto.LoginResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public LoginResponseDto AuthenticateAndGetToken(@RequestBody LoginRequestDto authRequestDTO) throws LoginException {
        log.info("User login request:", authRequestDTO);
        LoginResponseDto reponse = authService.login(authRequestDTO);
        return reponse;
    }}