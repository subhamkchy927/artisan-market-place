package com.artisan_market_place.requestDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchProductRequestDto {
    private Long sellerId;
    private Long companyName;
    private String productName;
    private BigDecimal lowerPrice;
    private BigDecimal higherPrice;
    private String category;
    private String brand;
    private Double weight;
    private String color;
}
