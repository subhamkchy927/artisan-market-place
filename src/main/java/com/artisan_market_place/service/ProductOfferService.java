package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.ProductOfferRequestDto;
import com.artisan_market_place.responseDto.ProductOfferResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface ProductOfferService {
    ProductOfferResponseDto createOffer(ProductOfferRequestDto dto, String loginUser);
    ProductOfferResponseDto updateOffer(ProductOfferRequestDto dto, Long offerId, String loginUser);
    ProductOfferResponseDto getOfferById(Long offerId, String loginUser);
    List<ProductOfferResponseDto> getAllOffers(String loginUser);
    List<ProductOfferResponseDto> getOffersByProductId(Long productId, String loginUser);
    List<ProductOfferResponseDto> getActiveOffers(String loginUser);
    List<ProductOfferResponseDto> getActiveOffersByProductId(Long productId, String loginUser);
    HashMap<String, String> deleteOffer(Long offerId, String loginUser);
}

