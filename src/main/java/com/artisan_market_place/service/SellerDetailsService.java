package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.SellerRequestDto;
import com.artisan_market_place.responseDto.SellerResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface SellerDetailsService {
    public SellerResponseDto createSeller(SellerRequestDto dto);
    public SellerResponseDto updateSeller(SellerRequestDto dto, Long sellerId);
    public SellerResponseDto getSellerById(Long sellerId);
    public SellerResponseDto cdeleteSeller(Long sellerId);
    public SellerResponseDto getAllSeller(Boolean isApplicationAdmin);
}
