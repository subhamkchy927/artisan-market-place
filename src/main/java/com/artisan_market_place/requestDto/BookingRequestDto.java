package com.artisan_market_place.requestDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookingRequestDto {
    private Long productId;
    private Long customerId;
    private Integer quantity;
    private Long deliveryAddressId;
    private String paymentMethod;
    private String notes;
    private Date deliveryDate;
}

