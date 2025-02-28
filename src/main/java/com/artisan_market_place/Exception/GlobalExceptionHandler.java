package com.artisan_market_place.Exception;

import com.artisan_market_place.responseDto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidationException(ValidationException ex) {
        ExceptionResponseDto response = ex.getExceptionResponse();
        log.error("LoginException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ExceptionResponseDto response = ex.getExceptionResponse();
        log.error("LoginException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponseDto> handleUnauthorizedException(UnauthorizedException ex) {
        ExceptionResponseDto response = ex.getExceptionResponse();
        log.error("LoginException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ExceptionResponseDto> handleInternalServerErrorException(InternalServerErrorException ex) {
        ExceptionResponseDto response = ex.getExceptionResponse();
        log.error("LoginException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponseDto> handleLoginException(LoginException ex) {
        ExceptionResponseDto response = ex.getExceptionResponse();
        log.error("LoginException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionResponseDto> handleAllUnhandledExceptions(Throwable ex) {
        ExceptionResponseDto response = new ExceptionResponseDto();
        response.setErrorMessage("An unexpected error occurred: " + ex.getMessage());
        response.setErrorCode("UNEXPECTED_ERROR");
        response.setStatusCode("500");
        response.setSeverity("HIGH");
        log.error("LoginException occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
