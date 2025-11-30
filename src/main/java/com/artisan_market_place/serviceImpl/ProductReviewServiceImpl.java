package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.ProdcutReviews;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.repository.ProductReviewsRepository;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.ProductReviewRequestDto;
import com.artisan_market_place.responseDto.ProductReviewResponseDto;
import com.artisan_market_place.service.ProductReviewService;
import com.artisan_market_place.utils.DateTimeUtil;
import com.artisan_market_place.validators.ProductReviewValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewsRepository productReviewsRepository;
    private final ProductReviewValidator productReviewValidator;
    private final ProductsRepositoryImpl productsRepository;
    private final UserRepository userRepository;

    public ProductReviewServiceImpl(ProductReviewsRepository productReviewsRepository, ProductReviewValidator productReviewValidator, ProductsRepositoryImpl productsRepository, UserRepository userRepository) {
        this.productReviewsRepository = productReviewsRepository;
        this.productReviewValidator = productReviewValidator;
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ProductReviewResponseDto createReview(ProductReviewRequestDto dto, String loginUser) {
        productReviewValidator.validateCreateReviewRequest(dto);
        
        ProdcutReviews review = new ProdcutReviews();
        review.setProdcutId(dto.getProductId());
        review.setCustomerId(dto.getCustomerId());
        review.setReviews(dto.getReviews());
        review.setRating(dto.getRating());
        
        // Calculate review and rating counts
        List<ProdcutReviews> existingReviews = productReviewsRepository.findByProdcutId(dto.getProductId());
        int reviewCount = existingReviews.size() + 1;
        double totalRating = existingReviews.stream().mapToDouble(ProdcutReviews::getRating).sum() + dto.getRating();
        double averageRating = totalRating / reviewCount;
        
        review.setReviewsCount(reviewCount);
        review.setRatingCount(averageRating);
        
        productReviewsRepository.save(review);
        
        // Update product rating if needed
        updateProductRating(dto.getProductId());
        
        return getReviewResponse(review);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ProductReviewResponseDto updateReview(ProductReviewRequestDto dto, Long reviewId, String loginUser) {
        productReviewValidator.validateUpdateReviewRequest(reviewId, dto);
        
        ProdcutReviews review = productReviewsRepository.findById(reviewId).orElseThrow();
        
        if (dto.getReviews() != null) review.setReviews(dto.getReviews());
        if (dto.getRating() != null) review.setRating(dto.getRating());
        
        // Recalculate counts
        List<ProdcutReviews> allReviews = productReviewsRepository.findByProdcutId(review.getProdcutId());
        review.setReviewsCount(allReviews.size());
        double averageRating = allReviews.stream().mapToDouble(ProdcutReviews::getRating).sum() / allReviews.size();
        review.setRatingCount(averageRating);
        
        productReviewsRepository.save(review);
        
        // Update product rating
        updateProductRating(review.getProdcutId());
        
        return getReviewResponse(review);
    }

    @Override
    public ProductReviewResponseDto getReviewById(Long reviewId, String loginUser) {
        ProdcutReviews review = productReviewValidator.validateReviewIdAndReturn(reviewId);
        return getReviewResponse(review);
    }

    @Override
    public List<ProductReviewResponseDto> getAllReviews(String loginUser) {
        List<ProdcutReviews> reviews = productReviewsRepository.findAll();
        return reviews.stream()
                .map(this::getReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReviewResponseDto> getReviewsByProductId(Long productId, String loginUser) {
        List<ProdcutReviews> reviews = productReviewsRepository.findByProdcutIdOrderByReviewIdDesc(productId);
        return reviews.stream()
                .map(this::getReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductReviewResponseDto getProductRatingSummary(Long productId, String loginUser) {
        productReviewValidator.validateProductId(productId);
        
        Double averageRating = productReviewsRepository.getAverageRatingByProductId(productId);
        Long reviewCount = productReviewsRepository.getReviewCountByProductId(productId);
        
        ProductReviewResponseDto response = new ProductReviewResponseDto();
        response.setProductId(productId);
        response.setAverageRating(averageRating != null ? averageRating : 0.0);
        response.setReviewsCount(reviewCount != null ? reviewCount.intValue() : 0);
        
        Optional<Products> productOpt = productsRepository.findById(productId);
        if (productOpt.isPresent()) {
            response.setProductName(productOpt.get().getName());
        }
        
        return response;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public HashMap<String, String> deleteReview(Long reviewId, String loginUser) {
        ProdcutReviews review = productReviewValidator.validateReviewIdAndReturn(reviewId);
        Long productId = review.getProdcutId();
        
        productReviewsRepository.delete(review);
        
        // Update product rating after deletion
        updateProductRating(productId);
        
        HashMap<String, String> response = new HashMap<>();
        response.put("reviewId", reviewId.toString());
        response.put("status", "Review deleted successfully");
        return response;
    }

    private void updateProductRating(Long productId) {
        Double averageRating = productReviewsRepository.getAverageRatingByProductId(productId);
        if (averageRating != null) {
            Optional<Products> productOpt = productsRepository.findById(productId);
            if (productOpt.isPresent()) {
                // Note: Products entity doesn't have rating field, but we can add it if needed
                // For now, we'll just update the review counts
            }
        }
    }

    private ProductReviewResponseDto getReviewResponse(ProdcutReviews review) {
        if (review == null) return null;
        
        ProductReviewResponseDto response = new ProductReviewResponseDto();
        response.setReviewId(review.getReviewId());
        response.setProductId(review.getProdcutId());
        response.setCustomerId(review.getCustomerId());
        response.setReviews(review.getReviews());
        response.setReviewsCount(review.getReviewsCount());
        response.setRating(review.getRating());
        response.setRatingCount(review.getRatingCount());
        
        // Fetch product name
        Optional<Products> productOpt = productsRepository.findById(review.getProdcutId());
        if (productOpt.isPresent()) {
            response.setProductName(productOpt.get().getName());
        }
        
        // Fetch customer name
        if (review.getCustomerId() != null) {
            Optional<Users> customerOpt = userRepository.findById(review.getCustomerId());
            if (customerOpt.isPresent()) {
                Users customer = customerOpt.get();
                response.setCustomerName(customer.getFirirstName() + " " + customer.getLastName());
            }
        }
        
        // Calculate average rating for this product
        Double averageRating = productReviewsRepository.getAverageRatingByProductId(review.getProdcutId());
        response.setAverageRating(averageRating != null ? averageRating : 0.0);
        
        return response;
    }
}

