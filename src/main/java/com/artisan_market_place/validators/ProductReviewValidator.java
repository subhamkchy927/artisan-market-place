package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.ProdcutReviews;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.enums.BookingStatusEnums;
import com.artisan_market_place.enums.UserRolesEnums;
import com.artisan_market_place.repository.BookingRepository;
import com.artisan_market_place.repository.ProductReviewsRepository;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.requestDto.ProductReviewRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewValidator {

    private final ProductReviewsRepository productReviewsRepository;
    private final ProductsRepositoryImpl productsRepository;
    private final GlobalValidator globalValidator;
    private final BookingRepository bookingRepository;

    public ProductReviewValidator(ProductReviewsRepository productReviewsRepository, ProductsRepositoryImpl productsRepository, GlobalValidator globalValidator, BookingRepository bookingRepository) {
        this.productReviewsRepository = productReviewsRepository;
        this.productsRepository = productsRepository;
        this.globalValidator = globalValidator;
        this.bookingRepository = bookingRepository;
    }

    public void validateCreateReviewRequest(ProductReviewRequestDto dto) {
        if (dto == null) throw new ValidationException(MessageConstants.REVIEW_OBJECT_NULL);
        if (dto.getProductId() == null) throw new ValidationException(MessageConstants.PRODUCT_ID_MANDATORY);
        if (dto.getCustomerId() == null) throw new ValidationException(MessageConstants.CUSTOMER_ID_MANDATORY);
        if (!StringUtils.hasText(dto.getReviews())) throw new ValidationException(MessageConstants.REVIEW_TEXT_MANDATORY);
        if (dto.getRating() == null) throw new ValidationException(MessageConstants.RATING_MANDATORY);
        
        if (dto.getRating() < 1.0 || dto.getRating() > 5.0) {
            throw new ValidationException(MessageConstants.RATING_INVALID);
        }
        
        validateProductId(dto.getProductId());
        
        Users customer = globalValidator.validateUserIdAndReturn(dto.getCustomerId());
        if (UserRolesEnums.CUSTOMER != customer.getRole() && UserRolesEnums.SELLER_CUSTOMER != customer.getRole()) {
            throw new ValidationException(MessageConstants.ONLY_CUSTOMER_CAN_BOOK);
        }
        
        // Validate that customer has a completed booking for this product
        List<com.artisan_market_place.entity.Booking> bookings = bookingRepository.findByProductIdAndCustomerIdAndStatusIn(
            dto.getProductId(), dto.getCustomerId(), 
            List.of(BookingStatusEnums.COMPLETED, BookingStatusEnums.DELIVERED)
        );
        if (bookings.isEmpty()) {
            throw new ValidationException(MessageConstants.CUSTOMER_MUST_HAVE_BOOKING);
        }
    }

    public void validateUpdateReviewRequest(Long reviewId, ProductReviewRequestDto dto) {
        if (dto == null) throw new ValidationException(MessageConstants.REVIEW_OBJECT_NULL);
        validateReviewId(reviewId);
        
        if (dto.getRating() != null && (dto.getRating() < 1.0 || dto.getRating() > 5.0)) {
            throw new ValidationException(MessageConstants.RATING_INVALID);
        }
    }

    public ProdcutReviews validateReviewIdAndReturn(Long reviewId) {
        Optional<ProdcutReviews> reviewOpt = productReviewsRepository.findById(reviewId);
        if (!reviewOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.REVIEW_NOT_FOUND);
        return reviewOpt.get();
    }

    public void validateReviewId(Long reviewId) {
        Optional<ProdcutReviews> reviewOpt = productReviewsRepository.findById(reviewId);
        if (!reviewOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.REVIEW_NOT_FOUND);
    }

    public void validateProductId(Long productId) {
        Optional<Products> productOpt = productsRepository.findById(productId);
        if (!productOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND);
    }
}

