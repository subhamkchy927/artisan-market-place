package com.artisan_market_place.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "seller_details")
@Entity
public class UserDetails extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private int sellerId;

    @Column(name = "seller_first_name", nullable = false)
    private String sellerFirstName;

    @Column(name = "seller_middle_name", nullable = false)
    private String sellerMiddleName;

    @Column(name = "seller_Last_name", nullable = false)
    private String sellerLastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "seller_rating")
    private String sellerRating;

    @Column(name = "is_application_admin")
    private Boolean isApplicationAdmin;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "password")
    private String password;

    @Column(name = "user_role")
    private String userRole;
}
