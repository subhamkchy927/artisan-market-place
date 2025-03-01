package com.artisan_market_place.service;

import com.artisan_market_place.entity.NotificationDetails;
import com.artisan_market_place.requestDto.NotificationRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public interface NotificationService{

    public void addIntoNotification(Long userId, String subject, String content);
}
