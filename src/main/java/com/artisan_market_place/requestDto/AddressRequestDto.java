package com.artisan_market_place.requestDto;

import lombok.Data;

@Data
public class AddressRequestDto {
    private Long userId;
    private String address1;
    private String address2;
    private String rawAddress;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Double latitude;
    private Double longitude;
}
