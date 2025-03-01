package com.artisan_market_place.requestDto;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    private String email;
    private String oldPassword;
    private String otp;
    private String newPassword;
    private String confirmPassword;
}
