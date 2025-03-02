package com.artisan_market_place.configurations;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TwilioConfig {
    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.sender-phone}")
    private String senderPhone;

    @Bean
    public TwilioRestClient initializeTwilio() {
        Twilio.init(accountSid, authToken);
        return Twilio.getRestClient();
    }
}

