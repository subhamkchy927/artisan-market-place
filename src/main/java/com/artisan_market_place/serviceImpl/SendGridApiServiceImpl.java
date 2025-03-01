package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.Exception.InternalServerErrorException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.service.SendGridApiService;
import com.sendgrid.SendGrid;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@Slf4j
public class SendGridApiServiceImpl implements SendGridApiService {

    @Value("${sendgrid.from}")
    private String fromEmail;
    private final SendGrid sendGrid;
    private final NotificationServiceImpl notificationService;

    public SendGridApiServiceImpl(SendGrid sendGrid, NotificationServiceImpl notificationService) {
        this.sendGrid = sendGrid;
        this.notificationService = notificationService;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String message,Long userId) throws IOException {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (Exception ex) {
            throw new InternalServerErrorException(MessageConstants.ERRROR_SENDING_EMAIL);
        }
        try {
            notificationService.addIntoNotification(userId, subject, message,toEmail);
        } catch (Exception ex) {
            log.info(MessageConstants.ERRROR_STORING_EMAIL_EMAIL);
        }
    }
}


