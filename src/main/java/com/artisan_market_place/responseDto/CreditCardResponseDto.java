package com.artisan_market_place.responseDto;

import com.artisan_market_place.enums.CreditCardTypesEnums;
import lombok.Data;

import java.util.Date;

@Data
public class CreditCardResponseDto {
    private Long cardId;
    private Long userId;
    private String cardHolderName;
    private String cardNumber;
    private Date expiryDate;
    private String cvv;
    private CreditCardTypesEnums cardType;
    private Boolean isActive = true;
}
