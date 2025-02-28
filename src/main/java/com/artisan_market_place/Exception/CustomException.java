package com.artisan_market_place.Exception;

import com.artisan_market_place.responseDto.ExceptionResponseDto;

public abstract  class CustomException extends RuntimeException {
   private final ExceptionResponseDto exceptionResponseDto;

     public CustomException(String message, String errorCode, String statusCode, String severity) {
        super(message);
        this.exceptionResponseDto = new ExceptionResponseDto();
        this.exceptionResponseDto.setErrorMessage(message);
        this.exceptionResponseDto.setErrorCode(errorCode);
        this.exceptionResponseDto.setStatusCode(statusCode);
        this.exceptionResponseDto.setSeverity(severity);
        }


     public ExceptionResponseDto getExceptionResponse() {
        return exceptionResponseDto;
     }
}