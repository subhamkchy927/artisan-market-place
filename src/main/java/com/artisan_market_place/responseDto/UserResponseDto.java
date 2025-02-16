package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class UserResponseDto {
    private int sellerId;
    private String sellerFirstName;
    private String sellerMiddleName;
    private String sellerLastName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String gstNumber;
    private String status;
    private String countryCode;
    private String sellerRating;
    private Boolean isApplicationAdmin;
    private String password;
    private String userRole;
}
