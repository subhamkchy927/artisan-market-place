package com.artisan_market_place.requestDto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequestDto {
    @NonNull
    private String userName;
    @NonNull
    private String password;
}
