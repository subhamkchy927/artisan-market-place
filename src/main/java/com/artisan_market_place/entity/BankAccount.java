package com.artisan_market_place.entity;

import com.artisan_market_place.enums.BankAccountTypesEnums;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "bank_account")
@Entity
public class BankAccount extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long bankAccountId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_holder_name", nullable = false, length = 128)
    private String accountHolderName;

    @Column(name = "account_number", nullable = false, unique = true, length = 32)
    private String accountNumber;

    @Column(name = "ifsc_code", nullable = false, length = 16)
    private String ifscCode;

    @Column(name = "bank_name", nullable = false, length = 64)
    private String bankName;

    @Column(name = "branch_name", nullable = false, length = 100)
    private String branchName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private BankAccountTypesEnums accountType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
