package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.configurations.TwilioConfig;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.service.NotificationService;
import com.artisan_market_place.service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioServiceImpl implements TwilioService {
    private final TwilioConfig twilioConfig;

    NotificationServiceImpl notificationService;

    public TwilioServiceImpl(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public String sendSms(Long userId,String subject,String email,String toMobile, String messageContent) {
        Message message = Message.creator(
                new PhoneNumber(toMobile),
                new PhoneNumber(twilioConfig.getSenderPhone()),
                messageContent
        ).create();
        try {
            notificationService.addIntoNotification(userId, subject, messageContent, email,true);
        }catch (Exception e){
            log.info(MessageConstants.ERROR_SENDING_SMS);
        }
        return message.getSid();
    }
}
