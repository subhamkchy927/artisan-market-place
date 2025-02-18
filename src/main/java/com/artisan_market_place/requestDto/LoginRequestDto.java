package com.artisan_market_place.requestDto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String userId;
    private String userName;
    private String password;
}
