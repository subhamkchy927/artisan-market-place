package com.artisan_market_place.serviceImpl;
import com.artisan_market_place.entity.BankAccount;
import com.artisan_market_place.repository.BankAccountRepository;
import com.artisan_market_place.requestDto.BankAccountRequestDto;
import com.artisan_market_place.responseDto.BankAccountResponseDto;
import com.artisan_market_place.service.BankAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BankAccountResponseDto addBankAccount(BankAccountRequestDto dto, String loginUser) {
        BankAccount bankAccount = setBankAccountDetails(new BankAccount(), dto);
        bankAccountRepository.saveAndFlush(bankAccount);
        return getBankAccountDetails(bankAccount);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BankAccountResponseDto updateBankAccount(BankAccountRequestDto dto, Long bankAccountId, String loginUser) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        bankAccount = setBankAccountDetails(bankAccount, dto);
        bankAccountRepository.save(bankAccount);
        return getBankAccountDetails(bankAccount);
    }

    @Override
    public BankAccountResponseDto getBankAccountById(Long bankAccountId, String loginUser) {
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        return getBankAccountDetails(bankAccount);
    }

    @Override
    public List<BankAccountResponseDto> getAllBankAccounts(Long userId, String loginUser) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByUserId(userId);
        return bankAccounts.stream().map(this::getBankAccountDetails).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Map<String, String> deleteBankAccount(Long bankAccountId, String loginUser) {
        Map<String, String> response = new HashMap<>();
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        bankAccountRepository.delete(bankAccount);
        response.put("bankAccountId", bankAccountId.toString());
        response.put("Status", "Success");
        return response;
    }

    private BankAccount setBankAccountDetails(BankAccount bankAccount, BankAccountRequestDto dto) {
        bankAccount.setUserId(dto.getUserId());
        bankAccount.setAccountHolderName(dto.getAccountHolderName());
        bankAccount.setAccountNumber(dto.getAccountNumber());
        bankAccount.setIfscCode(dto.getIfscCode());
        bankAccount.setBankName(dto.getBankName());
        bankAccount.setBranchName(dto.getBranchName());
        bankAccount.setAccountType(dto.getAccountType());
        bankAccount.setIsActive(dto.getIsActive());
        return bankAccount;
    }

    private BankAccountResponseDto getBankAccountDetails(BankAccount bankAccount) {
        BankAccountResponseDto responseDto = new BankAccountResponseDto();
        responseDto.setBankAccountId(bankAccount.getBankAccountId());
        responseDto.setUserId(bankAccount.getUserId());
        responseDto.setAccountHolderName(bankAccount.getAccountHolderName());
        responseDto.setAccountNumber(bankAccount.getAccountNumber());
        responseDto.setIfscCode(bankAccount.getIfscCode());
        responseDto.setBankName(bankAccount.getBankName());
        responseDto.setBranchName(bankAccount.getBranchName());
        responseDto.setAccountType(bankAccount.getAccountType());
        responseDto.setIsActive(bankAccount.getIsActive());
        return responseDto;
    }
}
