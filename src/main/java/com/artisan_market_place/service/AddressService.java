package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.AddressRequestDto;
import com.artisan_market_place.responseDto.AddressResponseDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface AddressService {
    public AddressResponseDto createAddress(AddressRequestDto dto, String loginUser);
    public AddressResponseDto updateAddress(AddressRequestDto dto, Long addressId, String loginUser);
    public AddressResponseDto getAddressById(Long addressId, String loginUser);
    public HashMap<String, String> deleteAddress(Long addressId, String loginUser);
    public List<AddressResponseDto> getAllAddresses(Long userId);
}
