package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.SellerDetails;
import com.artisan_market_place.repository.SellerDetailsRepository;
import com.artisan_market_place.requestDto.SellerRequestDto;
import com.artisan_market_place.responseDto.SellerResponseDto;
import com.artisan_market_place.service.SellerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerDetailsServiceImpl implements SellerDetailsService {

    @Autowired
    SellerDetailsRepository sellerRepository;

    @Override
    public SellerResponseDto createSeller(SellerRequestDto dto) {
        SellerDetails sellerDetails = setSellerDetails(dto);
        sellerRepository.save(sellerDetails);
        SellerResponseDto response = getSellerDetails(sellerDetails);
        return response;
    }

    @Override
    public SellerResponseDto updateSeller(SellerRequestDto dto, Long sellerId) {
        return null;
    }

    @Override
    public SellerResponseDto getSellerById(Long sellerId) {
        return null;
    }

    @Override
    public SellerResponseDto cdeleteSeller(Long sellerId) {
        return null;
    }

    @Override
    public SellerResponseDto getAllSeller(Boolean isApplicationAdmin) {
        return null;
    }

    private SellerDetails setSellerDetails(SellerRequestDto dto) {
        SellerDetails sellerDetails = new SellerDetails();
        sellerDetails.setSellerFirstName(dto.getSellerFirstName());
        sellerDetails.setSellerMiddleName(dto.getSellerMiddleName());
        sellerDetails.setSellerLastName(dto.getSellerLastName());
        sellerDetails.setEmail(dto.getEmail());
        sellerDetails.setPhoneNumber(dto.getPhoneNumber());
        sellerDetails.setCompanyName(dto.getCompanyName());
        sellerDetails.setGstNumber(dto.getGstNumber());
        sellerDetails.setStatus(dto.getStatus());
        sellerDetails.setSellerRating(dto.getSellerRating());
        sellerDetails.setIsApplicationAdmin(dto.getIsApplicationAdmin());
        sellerDetails.setCountryCode(dto.getCountryCode());
        sellerDetails.setAuditInfo("system");
        return sellerDetails;
    }

    private SellerResponseDto getSellerDetails(SellerDetails sellerDetails) {
        SellerResponseDto responseDto = new SellerResponseDto();
        responseDto.setSellerId(sellerDetails.getSellerId());
        responseDto.setSellerFirstName(sellerDetails.getSellerFirstName());
        responseDto.setSellerMiddleName(sellerDetails.getSellerMiddleName());
        responseDto.setSellerLastName(sellerDetails.getSellerLastName());
        responseDto.setEmail(sellerDetails.getEmail());
        responseDto.setPhoneNumber(sellerDetails.getPhoneNumber());
        responseDto.setCompanyName(sellerDetails.getCompanyName());
        responseDto.setGstNumber(sellerDetails.getGstNumber());
        responseDto.setStatus(sellerDetails.getStatus());
        responseDto.setSellerRating(sellerDetails.getSellerRating());
        responseDto.setCountryCode(sellerDetails.getCountryCode());
        responseDto.setIsApplicationAdmin(sellerDetails.getIsApplicationAdmin());
        return responseDto;
    }
//    git init
//    git add README.md
//    git commit -m "first commit"
//    git branch -M main
//    git remote add origin https://github.com/subhamkchy927/artisan-market-place.git
//    git push -u origin main
//
}
