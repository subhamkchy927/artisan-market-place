package com.artisan_market_place.serviceImpl;
import com.artisan_market_place.service.OtpService;

import java.security.SecureRandom;

public class OtpServiceImpl implements OtpService {

    @Override
    public String generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        return String.format("%04d", secureRandom.nextInt(10000));
    }
}
