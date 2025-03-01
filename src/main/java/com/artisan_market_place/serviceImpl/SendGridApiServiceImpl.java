package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.Exception.InternalServerErrorException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.service.SendGridApiService;
import com.sendgrid.SendGrid;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class SendGridApiServiceImpl implements SendGridApiService {

    @Value("${sendgrid.from}")
    private String fromEmail;
    private final SendGrid sendGrid;

    public SendGridApiServiceImpl(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String message) throws IOException {
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
        } catch (IOException ex) {
            throw new InternalServerErrorException(MessageConstants.ERRROR_SENDING_EMAIL);
        }
    }
}
