package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String userId;
    private String accessToken;
}
