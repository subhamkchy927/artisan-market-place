package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private Long userId;
    private String type = "Bearer";
    private String accessToken;
    private String companyName;
    private String role;
    private Boolean isAdmin;
}
