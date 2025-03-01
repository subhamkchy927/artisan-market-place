package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.NotificationDetails;
import com.artisan_market_place.enums.NotificationStatusEnums;
import com.artisan_market_place.repository.NotificationDetailsRepository;
import com.artisan_market_place.requestDto.NotificationRequestDto;
import com.artisan_market_place.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDetailsRepository notificationDetailsRepository;

    public NotificationServiceImpl(NotificationDetailsRepository notificationDetailsRepository) {
        this.notificationDetailsRepository = notificationDetailsRepository;
    }

    @Override
    public void addIntoNotification(Long userId, String subject, String content,String email) {
        NotificationDetails notificationDetails = new NotificationDetails();
        notificationDetails.setUserId(userId);
        notificationDetails.setIsEmail(true);
        notificationDetails.setIsSms(false);
        notificationDetails.setSubject(subject);
        notificationDetails.setContent(content);
        notificationDetails.setStatus(NotificationStatusEnums.SUCCESS);
        notificationDetails.setAuditInfo(email);
        notificationDetailsRepository.save(notificationDetails);
    }
}
