package com.artisan_market_place.Exception;

public class InternalServerErrorException extends CustomException {

    public InternalServerErrorException(String message,String errorCode) {
        super(message, errorCode, "500", "CRITICAL");
    }

    public InternalServerErrorException(String message) {
        super(message, null, "500", "CRITICAL");
    }
}
