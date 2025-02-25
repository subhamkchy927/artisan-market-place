package com.artisan_market_place.entity;

import com.artisan_market_place.enums.CreditCardTypesEnums;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "credit_card")
@Entity
public class CreditCard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "card_holder_name", nullable = false, length = 128)
    private String cardHolderName;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    @Column(name = "card_type", nullable = false, length = 32)
    private CreditCardTypesEnums cardType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
