package com.artisan_market_place.controllers;

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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JwtUtil jwtUtil;



    @PostMapping("/login")
    public LoginResponseDto AuthenticateAndGetToken(@RequestBody LoginRequestDto authRequestDTO){
        LoginResponseDto reponse = new  LoginResponseDto();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUserName(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtUtil.GenerateToken(authRequestDTO.getUserName());
            reponse.setAccessToken(token);
        }
        else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
        return reponse;
    }}