package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.UserDetails;
import com.artisan_market_place.repository.UserRepository;
import com.artisan_market_place.requestDto.UserRequestDto;
import com.artisan_market_place.responseDto.UserResponseDto;
import com.artisan_market_place.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository sellerRepository;

    @Override
    public UserResponseDto createSeller(UserRequestDto dto) {
        UserDetails userDetails = setSellerDetails(dto);
        sellerRepository.save(userDetails);
        return getSellerDetails(userDetails);
    }

    @Override
    public UserResponseDto updateSeller(UserRequestDto dto, Long sellerId) {
        Optional<UserDetails> optionalSeller = sellerRepository.findById(sellerId);
        if (optionalSeller.isPresent()) {
            UserDetails userDetails = optionalSeller.get();
            userDetails = setSellerDetails(dto);
            sellerRepository.save(userDetails);
            return getSellerDetails(userDetails);
        }
        return null;
    }

    @Override
    public UserResponseDto getSellerById(Long sellerId) {
        Optional<UserDetails> optionalSeller = sellerRepository.findById(sellerId);
        return optionalSeller.map(this::getSellerDetails).orElse(null);
    }

    @Override
    public HashMap<String, String> deleteSeller(Long sellerId) {
        HashMap<String, String> response = new HashMap<>();
        Optional<UserDetails> optionalSeller = sellerRepository.findById(sellerId);
        if (optionalSeller.isPresent()) {
            UserDetails userDetails = optionalSeller.get();
            sellerRepository.delete(userDetails);
        }
        response.put("Seller Id", sellerId.toString());
        response.put("Status", "Success");
        return response;
    }

    @Override
    public List<UserResponseDto> getAllSeller(Boolean isApplicationAdmin) {
        List<UserDetails> sellers = sellerRepository.findAll();
        return sellers.stream()
                .filter(seller -> isApplicationAdmin == null || seller.getIsApplicationAdmin().equals(isApplicationAdmin))
                .map(this::getSellerDetails)
                .collect(Collectors.toList());
    }


    private UserDetails setSellerDetails(UserRequestDto dto) {
        UserDetails userDetails = new UserDetails();
        userDetails.setSellerFirstName(dto.getSellerFirstName());
        userDetails.setSellerMiddleName(dto.getSellerMiddleName());
        userDetails.setSellerLastName(dto.getSellerLastName());
        userDetails.setEmail(dto.getEmail());
        userDetails.setPhoneNumber(dto.getPhoneNumber());
        userDetails.setCompanyName(dto.getCompanyName());
        userDetails.setGstNumber(dto.getGstNumber());
        userDetails.setStatus(dto.getStatus());
        userDetails.setSellerRating(dto.getSellerRating());
        userDetails.setIsApplicationAdmin(dto.getIsApplicationAdmin());
        userDetails.setCountryCode(dto.getCountryCode());
        userDetails.setPassword(dto.getPassword());
        userDetails.setCountryCode(dto.getUserRole());
        userDetails.setAuditInfo("system");
        return userDetails;
    }

    private UserResponseDto getSellerDetails(UserDetails userDetails) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setSellerId(userDetails.getSellerId());
        responseDto.setSellerFirstName(userDetails.getSellerFirstName());
        responseDto.setSellerMiddleName(userDetails.getSellerMiddleName());
        responseDto.setSellerLastName(userDetails.getSellerLastName());
        responseDto.setEmail(userDetails.getEmail());
        responseDto.setPhoneNumber(userDetails.getPhoneNumber());
        responseDto.setCompanyName(userDetails.getCompanyName());
        responseDto.setGstNumber(userDetails.getGstNumber());
        responseDto.setStatus(userDetails.getStatus());
        responseDto.setSellerRating(userDetails.getSellerRating());
        responseDto.setCountryCode(userDetails.getCountryCode());
        responseDto.setIsApplicationAdmin(userDetails.getIsApplicationAdmin());
        responseDto.setPassword(userDetails.getPassword());
        responseDto.setCountryCode(userDetails.getUserRole());
        return responseDto;
    }
}
