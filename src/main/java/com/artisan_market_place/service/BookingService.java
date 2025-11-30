package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.BookingRequestDto;
import com.artisan_market_place.responseDto.BookingResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto dto, String loginUser);
    BookingResponseDto updateBooking(BookingRequestDto dto, Long bookingId, String loginUser);
    BookingResponseDto getBookingById(Long bookingId, String loginUser);
    List<BookingResponseDto> getAllBookings(String loginUser);
    List<BookingResponseDto> getBookingsByCustomerId(Long customerId, String loginUser);
    List<BookingResponseDto> getBookingsBySellerId(Long sellerId, String loginUser);
    List<BookingResponseDto> getBookingsByStatus(String status, String loginUser);
    HashMap<String, String> cancelBooking(Long bookingId, String loginUser);
    HashMap<String, String> confirmBooking(Long bookingId, String loginUser);
    HashMap<String, String> completeBooking(Long bookingId, String loginUser);
}

