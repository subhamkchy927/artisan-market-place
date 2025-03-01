package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.NotificationDetails;
import com.artisan_market_place.enums.NotificationStatusEnums;
import com.artisan_market_place.repository.NotificationDetailsRepository;
import com.artisan_market_place.requestDto.NotificationRequestDto;
import com.artisan_market_place.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    private final NotificationDetailsRepository notificationDetailsRepository;

    public NotificationServiceImpl(NotificationDetailsRepository notificationDetailsRepository) {
        this.notificationDetailsRepository = notificationDetailsRepository;
    }

    @Override
    public void addIntoNotification(Long userId, String subject, String content) {
        NotificationDetails notificationDetails = new NotificationDetails();
        notificationDetails.setUserId(userId);
        notificationDetails.setIsEmail(true);
        notificationDetails.setIsSms(false);
        notificationDetails.setSubject(subject);
        notificationDetails.setContent(content);
        notificationDetails.setStatus(NotificationStatusEnums.SUCCESS);
        notificationDetailsRepository.save(notificationDetails);
    }
}
