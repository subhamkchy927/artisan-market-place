package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class ProductReviewResponseDto {
    private Long reviewId;
    private Long productId;
    private String productName;
    private Long customerId;
    private String customerName;
    private String reviews;
    private Integer reviewsCount;
    private Double rating;
    private Double ratingCount;
    private Double averageRating;
}

