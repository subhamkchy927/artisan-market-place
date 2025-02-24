package com.artisan_market_place.Exception;

import com.artisan_market_place.responseDto.ExceptionResponse;

public class LoginException extends CustomException {

    public LoginException(String errorMessage,String errorCode) {
        super(errorMessage, errorCode, "401", "HIGH");
    }

    public LoginException(String errorMessage) {
        super(errorMessage, null, "401", "HIGH");
    }
}