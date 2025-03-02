package com.artisan_market_place.service;

import org.springframework.stereotype.Service;

@Service
public interface TwilioService {
    public String sendSms(Long userId,String subject,String email,String toMobile, String messageContent);

    }
