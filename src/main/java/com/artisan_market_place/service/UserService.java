package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {
    public UserResponseDto createUser(UserRequestDto dto);
    public UserResponseDto updateUser(UserRequestDto dto, Long userId,String loginUser);
    public UserResponseDto getUserById(Long userId,String loginUser);
    public HashMap<String,String> deleteUser(Long userId,String loginUser);
    public List<UserResponseDto> getAllUser(Boolean isApplicationAdmin,String loginUser);
}
