package com.artisan_market_place.Exception;

import org.springframework.stereotype.Service;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(String message,String errorCode) {
        super(message, errorCode, "401", "HIGH");
    }

    public UnauthorizedException(String message) {
        super(message, null, "401", "HIGH");
    }
}
