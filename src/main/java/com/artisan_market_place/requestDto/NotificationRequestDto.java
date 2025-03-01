package com.artisan_market_place.requestDto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class NotificationRequestDto {
    private Long userId;
    private String subject;
    private Boolean isEmail;
    private Boolean isSms;
    private String content;
}
