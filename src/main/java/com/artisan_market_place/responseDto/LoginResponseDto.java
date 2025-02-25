package com.artisan_market_place.responseDto;

import com.artisan_market_place.enums.UserRolesEnums;
import lombok.Data;

@Data
public class LoginResponseDto {
    private Long userId;
    private String type = "Bearer";
    private String accessToken;
    private String companyName;
    private UserRolesEnums role;
    private Boolean isAdmin;
}
