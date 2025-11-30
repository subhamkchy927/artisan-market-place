package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.Booking;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.enums.BookingStatusEnums;
import com.artisan_market_place.enums.UserRolesEnums;
import com.artisan_market_place.repository.BookingRepository;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.requestDto.BookingRequestDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingValidator {

    private final BookingRepository bookingRepository;
    private final ProductsRepositoryImpl productsRepository;
    private final GlobalValidator globalValidator;

    public BookingValidator(BookingRepository bookingRepository, ProductsRepositoryImpl productsRepository, GlobalValidator globalValidator) {
        this.bookingRepository = bookingRepository;
        this.productsRepository = productsRepository;
        this.globalValidator = globalValidator;
    }

    public void validateCreateBookingRequest(BookingRequestDto dto) {
        if (dto == null) throw new ValidationException(MessageConstants.BOOKING_OBJECT_NULL);
        if (dto.getProductId() == null) throw new ValidationException(MessageConstants.PRODUCT_ID_MANDATORY);
        if (dto.getCustomerId() == null) throw new ValidationException(MessageConstants.CUSTOMER_ID_MANDATORY);
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) throw new ValidationException(MessageConstants.BOOKING_QUANTITY_INVALID);
        if (dto.getDeliveryAddressId() == null) throw new ValidationException(MessageConstants.DELIVERY_ADDRESS_ID_MANDATORY);

        Products product = validateProductIdAndReturn(dto.getProductId());
        if (product.getStockQuantity() < dto.getQuantity()) {
            throw new ValidationException(MessageConstants.INSUFFICIENT_STOCK);
        }

        Users customer = globalValidator.validateUserIdAndReturn(dto.getCustomerId());
        if (UserRolesEnums.CUSTOMER != customer.getRole() && UserRolesEnums.SELLER_CUSTOMER != customer.getRole()) {
            throw new ValidationException(MessageConstants.ONLY_CUSTOMER_CAN_BOOK);
        }
    }

    public void validateUpdateBookingRequest(Long bookingId, BookingRequestDto dto) {
        if (dto == null) throw new ValidationException(MessageConstants.BOOKING_OBJECT_NULL);
        Booking booking = validateBookingIdAndReturn(bookingId);
        
        if (dto.getQuantity() != null && dto.getQuantity() <= 0) {
            throw new ValidationException(MessageConstants.BOOKING_QUANTITY_INVALID);
        }

        if (booking.getStatus() == BookingStatusEnums.COMPLETED || booking.getStatus() == BookingStatusEnums.DELIVERED) {
            throw new ValidationException(MessageConstants.CANNOT_UPDATE_COMPLETED_BOOKING);
        }
    }

    public Booking validateBookingIdAndReturn(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (!bookingOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.BOOKING_NOT_FOUND);
        return bookingOpt.get();
    }

    public Products validateProductIdAndReturn(Long productId) {
        Optional<Products> productOpt = productsRepository.findById(productId);
        if (!productOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND);
        return productOpt.get();
    }

    public void validateBookingCancellation(Long bookingId, String loginUser) {
        Booking booking = validateBookingIdAndReturn(bookingId);
        if (booking.getStatus() == BookingStatusEnums.COMPLETED || booking.getStatus() == BookingStatusEnums.DELIVERED) {
            throw new ValidationException(MessageConstants.CANNOT_CANCEL_COMPLETED_BOOKING);
        }
        if (booking.getStatus() == BookingStatusEnums.CANCELLED) {
            throw new ValidationException(MessageConstants.BOOKING_ALREADY_CANCELLED);
        }
    }
}

