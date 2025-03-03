package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class ProdcutImagesResponseDto {
    private Long imageId;
    private Long productId;
    private String imageUrl;
}
