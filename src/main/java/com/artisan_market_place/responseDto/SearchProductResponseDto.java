package com.artisan_market_place.responseDto;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SearchProductResponseDto {
    private Long productId;
    private Long sellerId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String brand;
    private Double weight;
    private String dimensions;
    private String status;
    private String color;
    private String productType;
    private String sellerName;
    private String sellerPhone;
    private String sellerEmail;
    private Double sellerRating;
    private Double productRating;
    private Boolean isOffer;
    private BigDecimal flatDiscount;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minPurchaseAmount;
    private Date offerStartTime;
    private Date offerEndTime;
}
