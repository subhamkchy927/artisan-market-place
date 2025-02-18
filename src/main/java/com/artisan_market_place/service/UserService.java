package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {
    public UserResponseDto createUser(UserRequestDto dto);
    public UserResponseDto updateUser(UserRequestDto dto, Long sellerId);
    public UserResponseDto getUserById(Long sellerId);
    public HashMap<String,String> deleteUser(Long sellerId);
    public List<UserResponseDto> getAllUser(Boolean isApplicationAdmin);
}
