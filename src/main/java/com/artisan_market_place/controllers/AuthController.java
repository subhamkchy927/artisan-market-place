package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.AuthService;
import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.LoginRequestDto;
import com.artisan_market_place.responseDto.LoginResponseDto;
import com.artisan_market_place.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;


    @PostMapping("/login")
    public LoginResponseDto AuthenticateAndGetToken(@RequestBody LoginRequestDto authRequestDTO) throws LoginException {
        LoginResponseDto reponse = authService.login(authRequestDTO);
        return reponse;
    }}