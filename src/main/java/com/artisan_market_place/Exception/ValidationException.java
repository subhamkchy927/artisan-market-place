package com.artisan_market_place.Exception;

public class ValidationException extends CustomException {

    public ValidationException(String message,String errorCode) {
        super(message, errorCode, "400", "MEDIUM");
    }

    public ValidationException(String message) {
        super(message, null, "400", "MEDIUM");
    }
}
