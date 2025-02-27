package com.artisan_market_place.requestDto;

import com.artisan_market_place.enums.BankAccountTypesEnums;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class BankAccountRequestDto {
    private Long userId;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;
    private String accountType;
    private Boolean isActive = false;
}
