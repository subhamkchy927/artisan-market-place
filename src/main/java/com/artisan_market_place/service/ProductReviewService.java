package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.ProductReviewRequestDto;
import com.artisan_market_place.responseDto.ProductReviewResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ProductReviewService {
    ProductReviewResponseDto createReview(ProductReviewRequestDto dto, String loginUser);
    ProductReviewResponseDto updateReview(ProductReviewRequestDto dto, Long reviewId, String loginUser);
    ProductReviewResponseDto getReviewById(Long reviewId, String loginUser);
    List<ProductReviewResponseDto> getAllReviews(String loginUser);
    List<ProductReviewResponseDto> getReviewsByProductId(Long productId, String loginUser);
    ProductReviewResponseDto getProductRatingSummary(Long productId, String loginUser);
    HashMap<String, String> deleteReview(Long reviewId, String loginUser);
}

