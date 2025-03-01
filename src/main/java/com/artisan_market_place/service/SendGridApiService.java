package com.artisan_market_place.service;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface SendGridApiService {

    public void sendEmail(String toEmail, String subject, String message,Long userId) throws IOException;

}
