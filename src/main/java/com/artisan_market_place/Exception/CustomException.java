package com.artisan_market_place.Exception;

import com.artisan_market_place.responseDto.ExceptionResponse;
import org.springframework.stereotype.Service;

public abstract  class CustomException extends RuntimeException {
   private final ExceptionResponse exceptionResponse;

     public CustomException(String message, String errorCode, String statusCode, String severity) {
        super(message);
        this.exceptionResponse = new ExceptionResponse();
        this.exceptionResponse.setErrorMessage(message);
        this.exceptionResponse.setErrorCode(errorCode);
        this.exceptionResponse.setStatusCode(statusCode);
        this.exceptionResponse.setSeverity(severity);
        }


     public ExceptionResponse getExceptionResponse() {
        return exceptionResponse;
     }
}