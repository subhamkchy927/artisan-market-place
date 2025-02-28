package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class ExceptionResponseDto {
    private String errorMessage;
    private String errorCode;
    private String statusCode;
    private String severity;

    }

