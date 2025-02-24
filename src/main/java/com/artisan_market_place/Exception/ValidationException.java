package com.artisan_market_place.Exception;

import com.artisan_market_place.responseDto.ExceptionResponse;
import org.springframework.stereotype.Service;

public class ValidationException extends CustomException {

    public ValidationException(String message,String errorCode) {
        super(message, errorCode, "400", "MEDIUM");
    }

    public ValidationException(String message) {
        super(message, null, "400", "MEDIUM");
    }
}
