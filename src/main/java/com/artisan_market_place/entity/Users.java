package com.artisan_market_place.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "user_details")
@Entity
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firirstName;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

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

    @Column(name = "rating")
    private String rating;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "role")
    private String role;
}