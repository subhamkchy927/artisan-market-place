package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.AuthService;
import com.artisan_market_place.requestDto.LoginRequestDto;
import com.artisan_market_place.requestDto.ResetPasswordRequestDto;
import com.artisan_market_place.responseDto.LoginResponseDto;
import com.artisan_market_place.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserServiceImpl userService;
    public AuthController(AuthService authService, UserServiceImpl userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/login")
    public LoginResponseDto AuthenticateAndGetToken(@RequestBody LoginRequestDto authRequestDTO) throws LoginException {
        log.info("User login request:", authRequestDTO);
        LoginResponseDto reponse = authService.login(authRequestDTO);
        return reponse;
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<HashMap<String, String>> resetUserPassword(
            @RequestBody ResetPasswordRequestDto request) {
        log.info("Resetting password for email: {}, isForgot: {}", request.getEmail(), true);
        HashMap<String, String> response = userService.resetUserPassword(request, true);
        return ResponseEntity.ok(response);
    }

}