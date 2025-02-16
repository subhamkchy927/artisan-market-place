package com.artisan_market_place.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long userId;
    private String companyName;
    private boolean isRoot;
    private String username;
    private List<String> roles;
}
