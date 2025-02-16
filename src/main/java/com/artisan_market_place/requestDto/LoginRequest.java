package com.artisan_market_place.requestDto;

import lombok.Data;

@Data
public class LoginRequest {
    private Long userId;
    private String userName;
    private String password;
    private String refreshToken;
}
