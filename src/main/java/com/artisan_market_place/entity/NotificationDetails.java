package com.artisan_market_place.entity;

import com.artisan_market_place.enums.NotificationStatusEnums;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "notification_details")
public class NotificationDetails extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "is_email")
    private Boolean isEmail;

    @Column(name = "is_sms")
    private Boolean isSms;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private NotificationStatusEnums status;
}
