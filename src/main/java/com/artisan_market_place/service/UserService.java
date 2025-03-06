package com.artisan_market_place.service;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.constants.NotificationConstant;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.entity.UsersLoginInfo;
import com.artisan_market_place.enums.UserStatusEnums;
import com.artisan_market_place.requestDto.ResetPasswordRequestDto;
import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {
    public UserResponseDto createUser(UserRequestDto dto);
    public UserResponseDto updateUser(UserRequestDto dto, Long userId,String loginUser);
    public UserResponseDto getUserById(Long userId,String loginUser);
    public HashMap<String,String> deleteUser(Long userId,String loginUser);
    public List<UserResponseDto> getAllUser(Boolean isApplicationAdmin,String loginUser);
    public HashMap<String, String> sendVerificationOtpToUser(String email,String phonNumber) throws IOException;
    public HashMap<String, String> verifyUserOtp(String email,String phonNumber, String otp);
    public HashMap<String, String> resetUserPassword(ResetPasswordRequestDto request, Boolean isForgot);
}
