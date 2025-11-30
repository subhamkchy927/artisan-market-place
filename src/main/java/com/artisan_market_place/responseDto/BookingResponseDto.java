package com.artisan_market_place.responseDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookingResponseDto {
    private Long bookingId;
    private Long productId;
    private String productName;
    private Long customerId;
    private String customerName;
    private Long sellerId;
    private String sellerName;
    private Long deliveryPersonId;
    private String deliveryPersonName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discountAmount;
    private BigDecimal finalPrice;
    private Date bookingDate;
    private Date deliveryDate;
    private Long deliveryAddressId;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private String notes;
    private Date creationDate;
    private Date lastUpdateDate;
}

