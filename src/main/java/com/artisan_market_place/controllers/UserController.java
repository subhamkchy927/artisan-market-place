package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.ResetPasswordRequestDto;
import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v2/user")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;
    public UserController(UserServiceImpl userService, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @RequestBody UserRequestDto dto,
            @PathVariable Long userId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Request to update user with ID: {}", userId);
        String loginUser = jwtUtil.extractUsername(token);
        UserResponseDto response = userService.updateUser(dto, userId,loginUser);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getuserById(@PathVariable Long userId,@RequestHeader(value = "Authorization") String token) {
        log.info("Fetching user with ID: {}", userId);
        String loginUser = jwtUtil.extractUsername(token);
        UserResponseDto response = userService.getUserById(userId,loginUser);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HashMap<String,String>> deleteUser(@PathVariable Long userId,@RequestHeader(value = "Authorization") String token) {
        log.info("Request to delete seluserler with ID: {}", userId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String,String> response = userService.deleteUser(userId,loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @RequestParam(required = false) Boolean isApplicationAdmin,@RequestHeader(value = "Authorization") String token) {
        log.info("Fetching all users. isApplicationAdmin: {}", isApplicationAdmin);
        String loginUser = jwtUtil.extractUsername(token);
        List<UserResponseDto> user = userService.getAllUser(isApplicationAdmin,loginUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<HashMap<String, String>> sendVerificationOtpToUser(
            @RequestParam String email) throws  IOException {
        log.info("Sending verification OTP to email: {}", email);
        HashMap<String, String> response = userService.sendVerificationOtpToUser(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<HashMap<String, String>> verifyUserOtp(
            @RequestParam String email,
            @RequestParam String otp) {
        log.info("Verifying OTP for email: {}", email);
        HashMap<String, String> response = userService.verifyUserOtp(email, otp);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<HashMap<String, String>> resetUserPassword(
            @RequestBody ResetPasswordRequestDto request) {
        log.info("Resetting password for email: {}, isForgot: {}", request.getEmail(), false);
        HashMap<String, String> response = userService.resetUserPassword(request, false);
        return ResponseEntity.ok(response);
    }
}


