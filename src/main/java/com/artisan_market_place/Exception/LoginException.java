package com.artisan_market_place.Exception;

public class LoginException extends CustomException {

    public LoginException(String errorMessage,String errorCode) {
        super(errorMessage, errorCode, "401", "HIGH");
    }

    public LoginException(String errorMessage) {
        super(errorMessage, null, "401", "HIGH");
    }
}