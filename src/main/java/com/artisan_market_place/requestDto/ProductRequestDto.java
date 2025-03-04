package com.artisan_market_place.requestDto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequestDto {
    private Long userId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer totalQuantity;
    private Integer stockQuantity;
    private String category;
    private String brand;
    private Double weight;
    private String dimensions;
    private String status;
    private String color;
    private String productType;
    private List<ProdcutImagesRequestDto> images;
}
