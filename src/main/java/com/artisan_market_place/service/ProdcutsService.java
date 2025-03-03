package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.ProductRequestDto;
import com.artisan_market_place.responseDto.ProdcutResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ProdcutsService {
    ProdcutResponseDto createProduct(ProductRequestDto dto, String loginUser);
    ProdcutResponseDto updateProduct(ProductRequestDto dto, Long productId, String loginUser);
    ProdcutResponseDto getProductById(Long productId, String loginUser);
    List<ProdcutResponseDto> getAllProducts(String loginUser);
    HashMap<String, String> deleteProduct(Long productId, String loginUser);
    List<ProdcutResponseDto> getProductsByCategory(Long userId, String category, String loginUser);

}
