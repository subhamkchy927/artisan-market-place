package com.artisan_market_place.requestDto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProdcutImagesRequestDto {
    private Long productId;
    private String imageUrl;
    private String imageName;
}
