package com.artisan_market_place.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "admin_address")
@Entity
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address1", nullable = false, length = 256)
    private String address1;

    @Column(name = "address2", length = 256)
    private String address2;

    @Column(name = "raw_address", length = 256)
    private String rawAddress;

    @Column(name = "city", nullable = false, length = 64)
    private String city;

    @Column(name = "state", nullable = false, length = 64)
    private String state;

    @Column(name = "country", nullable = false, length = 32)
    private String country;

    @Column(name = "zip_code", nullable = false, length = 32)
    private String zipCode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}