package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {
    public UserResponseDto createSeller(UserRequestDto dto);
    public UserResponseDto updateSeller(UserRequestDto dto, Long sellerId);
    public UserResponseDto getSellerById(Long sellerId);
    public HashMap<String,String> deleteSeller(Long sellerId);
    public List<UserResponseDto> getAllSeller(Boolean isApplicationAdmin);
}
