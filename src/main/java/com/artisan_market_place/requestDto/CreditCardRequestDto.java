package com.artisan_market_place.requestDto;

import com.artisan_market_place.enums.CreditCardTypesEnums;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
public class CreditCardRequestDto {
    private Long userId;
    private String cardHolderName;
    private String cardNumber;
    private Date expiryDate;
    private String cvv;
    private CreditCardTypesEnums cardType;
    private Boolean isActive = true;
}
