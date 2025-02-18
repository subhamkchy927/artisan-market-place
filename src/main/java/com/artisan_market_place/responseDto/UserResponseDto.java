package com.artisan_market_place.responseDto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long sellerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String gstNumber;
    private String status;
    private String countryCode;
    private String rating;
    private Boolean isApplicationAdmin;
    private String userRole;
}
