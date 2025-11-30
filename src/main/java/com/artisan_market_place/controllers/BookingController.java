package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.BookingRequestDto;
import com.artisan_market_place.responseDto.BookingResponseDto;
import com.artisan_market_place.serviceImpl.BookingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v2/booking")
@Slf4j
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final JwtUtil jwtUtil;

    public BookingController(BookingServiceImpl bookingService, JwtUtil jwtUtil) {
        this.bookingService = bookingService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public ResponseEntity<BookingResponseDto> createBooking(
            @RequestBody BookingRequestDto dto,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Creating booking: {}", dto);
        String loginUser = jwtUtil.extractUsername(token);
        BookingResponseDto response = bookingService.createBooking(dto, loginUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> updateBooking(
            @RequestBody BookingRequestDto dto,
            @PathVariable Long bookingId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Updating booking with ID: {}", bookingId);
        String loginUser = jwtUtil.extractUsername(token);
        BookingResponseDto response = bookingService.updateBooking(dto, bookingId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBookingById(
            @PathVariable Long bookingId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching booking with ID: {}", bookingId);
        String loginUser = jwtUtil.extractUsername(token);
        BookingResponseDto response = bookingService.getBookingById(bookingId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<BookingResponseDto>> getAllBookings(
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching all bookings");
        String loginUser = jwtUtil.extractUsername(token);
        List<BookingResponseDto> response = bookingService.getAllBookings(loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByCustomerId(
            @PathVariable Long customerId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching bookings for customer ID: {}", customerId);
        String loginUser = jwtUtil.extractUsername(token);
        List<BookingResponseDto> response = bookingService.getBookingsByCustomerId(customerId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsBySellerId(
            @PathVariable Long sellerId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching bookings for seller ID: {}", sellerId);
        String loginUser = jwtUtil.extractUsername(token);
        List<BookingResponseDto> response = bookingService.getBookingsBySellerId(sellerId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByStatus(
            @PathVariable String status,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching bookings with status: {}", status);
        String loginUser = jwtUtil.extractUsername(token);
        List<BookingResponseDto> response = bookingService.getBookingsByStatus(status, loginUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<HashMap<String, String>> cancelBooking(
            @PathVariable Long bookingId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Cancelling booking with ID: {}", bookingId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String, String> response = bookingService.cancelBooking(bookingId, loginUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<HashMap<String, String>> confirmBooking(
            @PathVariable Long bookingId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Confirming booking with ID: {}", bookingId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String, String> response = bookingService.confirmBooking(bookingId, loginUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{bookingId}/complete")
    public ResponseEntity<HashMap<String, String>> completeBooking(
            @PathVariable Long bookingId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Completing booking with ID: {}", bookingId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String, String> response = bookingService.completeBooking(bookingId, loginUser);
        return ResponseEntity.ok(response);
    }
}

