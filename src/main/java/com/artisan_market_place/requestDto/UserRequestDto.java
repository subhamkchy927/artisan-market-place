package com.artisan_market_place.requestDto;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserRequestDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String gstNumber;
    private String status;
    private String sellerRating;
    private String countryCode;
    private Boolean isApplicationAdmin;
    private String password;
    private String userRole;
}
