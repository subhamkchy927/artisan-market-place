package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.ApplicationConstants;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.BankAccount;
import com.artisan_market_place.repository.BankAccountRepository;
import com.artisan_market_place.requestDto.BankAccountRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class BankAccountValidator {
    private final BankAccountRepository bankAccountRepository;
    private final GlobalValidatorService globalValidatorService;

    public BankAccountValidator(BankAccountRepository bankAccountRepository, GlobalValidatorService globalValidatorService) {
        this.bankAccountRepository = bankAccountRepository;
        this.globalValidatorService = globalValidatorService;
    }

    public void validateMandatory(BankAccountRequestDto bankAccount) {
        if (bankAccount == null) throw new ValidationException(MessageConstants.BANK_ACCOUNT_OBJECT_NULL);
        if(bankAccount.getUserId() == null) throw new ValidationException(MessageConstants.ASSOCIATED_USER_ID_MANDATORY);
        if (!StringUtils.hasText(bankAccount.getAccountHolderName()))throw new ValidationException(MessageConstants.ACCOUNT_HOLDER_NAME_MANDATORY);
        if (!StringUtils.hasText(bankAccount.getAccountNumber()))throw new ValidationException(MessageConstants.ACCOUNT_NUMBER_MANDATORY);
        if (!StringUtils.hasText(bankAccount.getIfscCode()))throw new ValidationException(MessageConstants.IFSC_CODE_MANDATORY);
        if (!StringUtils.hasText(bankAccount.getBankName()))throw new ValidationException(MessageConstants.BANK_NAME_MANDATORY);
        if (!StringUtils.hasText(bankAccount.getBranchName()))throw new ValidationException(MessageConstants.BRANCH_NAME_MANDATORY);
        if (bankAccount.getAccountType() == null)throw new ValidationException(MessageConstants.ACCOUNT_TYPE_MANDATORY);
    }

    public void validateCreateBankAccountRequest(BankAccountRequestDto bankAccount) {
        validateMandatory(bankAccount);
        validateRequest(bankAccount);
        globalValidatorService.validateUserId(bankAccount.getUserId());
        if (bankAccountRepository.existsByAccountNumberAndUserId(bankAccount.getAccountNumber(),bankAccount.getUserId()))throw new ValidationException(MessageConstants.ACCOUNT_NUMBER_ALREADY_EXISTS);
    }

    public void validateUpdateBankAccountRequest(Long bankAccountId, BankAccountRequestDto bankAccount) {
        validateMandatory(bankAccount);
        validateRequest(bankAccount);
        globalValidatorService.validateUserId(bankAccount.getUserId());
        if (bankAccountRepository.existsByAccountNumberAndUserIdAndBankAccountIdNot(bankAccount.getAccountNumber(),bankAccount.getUserId(), bankAccountId))throw new ValidationException(MessageConstants.ACCOUNT_NUMBER_ALREADY_EXISTS);
    }

    private void validateRequest(BankAccountRequestDto bankAccount) {
        if (bankAccount.getIfscCode().length() != 11) throw new ValidationException(MessageConstants.INVALID_IFSC_CODE);
        if (bankAccount.getAccountNumber().length() < 6 || bankAccount.getAccountNumber().length() > 18) throw new ValidationException(MessageConstants.INVALID_ACCOUNT_NUMBER_LENGTH);
    }

    public BankAccount validateBankAccountIdAndReturn(Long bankAccountId) {
        Optional<BankAccount> bankAccountOpt = Optional.empty();
        bankAccountOpt = bankAccountRepository.findById(bankAccountId);
        if (!bankAccountOpt.isPresent())throw new ResourceNotFoundException(MessageConstants.BANK_ACCOUNT_NOT_FOUND);
        return bankAccountOpt.get();
    }
}
