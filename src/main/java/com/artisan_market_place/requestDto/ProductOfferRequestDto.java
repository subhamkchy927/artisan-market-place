package com.artisan_market_place.requestDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductOfferRequestDto {
    private Long productId;
    private String customerType;
    private String offerName;
    private String offerDescription;
    private Double percentageDiscount;
    private BigDecimal flatDiscount;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minPurchaseAmount;
    private Date startTime;
    private Date endTime;
    private String offerStatus;
}

