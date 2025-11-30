package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.ProductReviewRequestDto;
import com.artisan_market_place.responseDto.ProductReviewResponseDto;
import com.artisan_market_place.serviceImpl.ProductReviewServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v2/review")
@Slf4j
public class ProductReviewController {

    private final ProductReviewServiceImpl productReviewService;
    private final JwtUtil jwtUtil;

    public ProductReviewController(ProductReviewServiceImpl productReviewService, JwtUtil jwtUtil) {
        this.productReviewService = productReviewService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public ResponseEntity<ProductReviewResponseDto> createReview(
            @RequestBody ProductReviewRequestDto dto,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Creating review: {}", dto);
        String loginUser = jwtUtil.extractUsername(token);
        ProductReviewResponseDto response = productReviewService.createReview(dto, loginUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ProductReviewResponseDto> updateReview(
            @RequestBody ProductReviewRequestDto dto,
            @PathVariable Long reviewId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Updating review with ID: {}", reviewId);
        String loginUser = jwtUtil.extractUsername(token);
        ProductReviewResponseDto response = productReviewService.updateReview(dto, reviewId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ProductReviewResponseDto> getReviewById(
            @PathVariable Long reviewId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching review with ID: {}", reviewId);
        String loginUser = jwtUtil.extractUsername(token);
        ProductReviewResponseDto response = productReviewService.getReviewById(reviewId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductReviewResponseDto>> getAllReviews(
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching all reviews");
        String loginUser = jwtUtil.extractUsername(token);
        List<ProductReviewResponseDto> response = productReviewService.getAllReviews(loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductReviewResponseDto>> getReviewsByProductId(
            @PathVariable Long productId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching reviews for product ID: {}", productId);
        String loginUser = jwtUtil.extractUsername(token);
        List<ProductReviewResponseDto> response = productReviewService.getReviewsByProductId(productId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}/summary")
    public ResponseEntity<ProductReviewResponseDto> getProductRatingSummary(
            @PathVariable Long productId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching rating summary for product ID: {}", productId);
        String loginUser = jwtUtil.extractUsername(token);
        ProductReviewResponseDto response = productReviewService.getProductRatingSummary(productId, loginUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<HashMap<String, String>> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Deleting review with ID: {}", reviewId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String, String> response = productReviewService.deleteReview(reviewId, loginUser);
        return ResponseEntity.ok(response);
    }
}

