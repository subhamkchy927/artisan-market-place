package com.artisan_market_place.serviceImpl;
import com.artisan_market_place.entity.CreditCard;
import com.artisan_market_place.repository.CreditCardRepository;
import com.artisan_market_place.requestDto.CreditCardRequestDto;
import com.artisan_market_place.responseDto.CreditCardResponseDto;
import com.artisan_market_place.service.CreditCardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public CreditCardResponseDto addCreditCard(CreditCardRequestDto dto, String loginUser) {
        CreditCard creditCard = new CreditCard();
        creditCard = setCreditCardDetails(creditCard, dto);
        creditCardRepository.saveAndFlush(creditCard);
        return getCreditCardDetails(creditCard);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public CreditCardResponseDto updateCreditCard(CreditCardRequestDto dto, Long cardId, String loginUser) {
        CreditCard creditCard = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Credit Card not found"));
        creditCard = setCreditCardDetails(creditCard, dto);
        creditCardRepository.save(creditCard);
        return getCreditCardDetails(creditCard);
    }

    @Override
    public CreditCardResponseDto getCreditCardById(Long cardId, String loginUser) {
        CreditCard creditCard = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Credit Card not found"));
        return getCreditCardDetails(creditCard);
    }

    @Override
    public List<CreditCardResponseDto> getAllCreditCards(Long userId, String loginUser) {
        List<CreditCard> creditCards = creditCardRepository.findByUserId(userId);
        return creditCards.stream()
                .map(this::getCreditCardDetails)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> deleteCreditCard(Long cardId, String loginUser) {
        Map<String, String> response = new HashMap<>();
        CreditCard creditCard = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Credit Card not found"));
        creditCardRepository.delete(creditCard);
        response.put("cardId", cardId.toString());
        response.put("Status", "Success");
        return response;
    }

    private CreditCard setCreditCardDetails(CreditCard creditCard, CreditCardRequestDto dto) {
        creditCard.setUserId(dto.getUserId());
        creditCard.setCardHolderName(dto.getCardHolderName());
        creditCard.setCardNumber(dto.getCardNumber());
        creditCard.setExpiryDate(dto.getExpiryDate());
        creditCard.setCvv(dto.getCvv());
        creditCard.setCardType(dto.getCardType());
        creditCard.setIsActive(dto.getIsActive());
        return creditCard;
    }

    private CreditCardResponseDto getCreditCardDetails(CreditCard creditCard) {
        CreditCardResponseDto responseDto = new CreditCardResponseDto();
        responseDto.setCardId(creditCard.getCardId());
        responseDto.setUserId(creditCard.getUserId());
        responseDto.setCardHolderName(creditCard.getCardHolderName());
        responseDto.setCardNumber(creditCard.getCardNumber());
        responseDto.setExpiryDate(creditCard.getExpiryDate());
        responseDto.setCvv(creditCard.getCvv());
        responseDto.setCardType(creditCard.getCardType());
        responseDto.setIsActive(creditCard.getIsActive());
        return responseDto;
    }
}