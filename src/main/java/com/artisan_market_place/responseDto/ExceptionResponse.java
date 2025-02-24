package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String errorMessage;
    private String errorCode;
    private String statusCode;
    private String severity;

    }

