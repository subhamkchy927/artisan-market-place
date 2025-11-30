package com.artisan_market_place.requestDto;

import lombok.Data;

@Data
public class ProductReviewRequestDto {
    private Long productId;
    private Long customerId;
    private String reviews;
    private Double rating;
}

