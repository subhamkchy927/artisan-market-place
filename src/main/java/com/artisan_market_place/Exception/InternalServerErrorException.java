package com.artisan_market_place.Exception;

import com.artisan_market_place.responseDto.ExceptionResponse;
import org.springframework.stereotype.Service;

public class InternalServerErrorException extends CustomException {

    public InternalServerErrorException(String message,String errorCode) {
        super(message, errorCode, "500", "CRITICAL");
    }

    public InternalServerErrorException(String message) {
        super(message, null, "500", "CRITICAL");
    }
}
