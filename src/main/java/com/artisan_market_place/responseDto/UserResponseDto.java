package com.artisan_market_place.responseDto;

import com.artisan_market_place.enums.UserRolesEnums;
import com.artisan_market_place.enums.UserStatusEnums;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String gstNumber;
    private UserStatusEnums status;
    private String countryCode;
    private Double rating;
    private Boolean isApplicationAdmin;
    private UserRolesEnums userRole;
    List<AddressResponseDto> address;
}
