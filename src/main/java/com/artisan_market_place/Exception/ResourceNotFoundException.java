package com.artisan_market_place.Exception;

import org.springframework.stereotype.Service;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String message,String errorCode) {
        super(message,errorCode, "404", "MEDIUM");
    }

    public ResourceNotFoundException(String message) {
        super(message,null, "404", "MEDIUM");
    }
}