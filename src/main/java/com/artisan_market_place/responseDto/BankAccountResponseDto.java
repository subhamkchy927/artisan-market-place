package com.artisan_market_place.responseDto;

import com.artisan_market_place.enums.BankAccountTypesEnums;
import lombok.Data;

@Data
public class BankAccountResponseDto {
    private Long bankAccountId;
    private Long userId;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private String branchName;
    private BankAccountTypesEnums accountType;
    private Boolean isActive = true;
}
