package com.artisan_market_place.responseDto;

import com.artisan_market_place.requestDto.ProdcutImagesRequestDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProdcutResponseDto {
    private Long productId;
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
    private List<ProdcutImagesResponseDto> images;
}
