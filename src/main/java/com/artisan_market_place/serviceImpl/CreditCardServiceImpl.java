package com.artisan_market_place.serviceImpl;
import com.artisan_market_place.constants.ApplicationConstants;
import com.artisan_market_place.entity.CreditCard;
import com.artisan_market_place.enums.CreditCardTypesEnums;
import com.artisan_market_place.repository.CreditCardRepository;
import com.artisan_market_place.requestDto.CreditCardRequestDto;
import com.artisan_market_place.responseDto.CreditCardResponseDto;
import com.artisan_market_place.service.CreditCardService;
import com.artisan_market_place.utils.DateTimeUtil;
import com.artisan_market_place.validators.CreditCardValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final CreditCardValidator creditCardValidator;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository, CreditCardValidator creditCardValidator) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardValidator = creditCardValidator;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public CreditCardResponseDto addCreditCard(CreditCardRequestDto dto, String loginUser) {
        creditCardValidator.validateCreateCardRequest(dto);
        CreditCard creditCard = new CreditCard();
        creditCard = setCreditCardDetails(creditCard, dto,loginUser);
        creditCardRepository.saveAndFlush(creditCard);
        return getCreditCardDetails(creditCard);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public CreditCardResponseDto updateCreditCard(CreditCardRequestDto dto, Long cardId, String loginUser) {
        creditCardValidator.validateUpdateCardRequest(cardId,dto);
        CreditCard creditCard = creditCardValidator.validateCardIdAndReturn(cardId);
        creditCard = setCreditCardDetails(creditCard, dto,loginUser);
        creditCardRepository.save(creditCard);
        return getCreditCardDetails(creditCard);
    }

    @Override
    public CreditCardResponseDto getCreditCardById(Long cardId, String loginUser) {
        CreditCard creditCard = creditCardValidator.validateCardIdAndReturn(cardId);
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
        CreditCard creditCard = creditCardValidator.validateCardIdAndReturn(cardId);
        creditCardRepository.delete(creditCard);
        response.put("cardId", cardId.toString());
        response.put("Status", "Success");
        return response;
    }

    private CreditCard setCreditCardDetails(CreditCard creditCard, CreditCardRequestDto dto,String loginUser) {
        creditCard.setUserId(dto.getUserId());
        creditCard.setCardHolderName(dto.getCardHolderName());
        creditCard.setCardNumber(dto.getCardNumber());
        creditCard.setExpiryDate(dto.getExpiryDate());
        creditCard.setCvv(dto.getCvv());
        creditCard.setCardType(CreditCardTypesEnums.valueOf(dto.getCardType()));
        creditCard.setIsActive(dto.getIsActive());
        creditCard.setAuditInfo(loginUser);
        return creditCard;
    }

    private CreditCardResponseDto getCreditCardDetails(CreditCard creditCard) {
        CreditCardResponseDto responseDto = new CreditCardResponseDto();
        responseDto.setCardId(creditCard.getCardId());
        responseDto.setUserId(creditCard.getUserId());
        responseDto.setCardHolderName(creditCard.getCardHolderName());
        responseDto.setCardNumber(creditCard.getCardNumber());
        responseDto.setExpiryDate(DateTimeUtil.formateDate(creditCard.getExpiryDate(),ApplicationConstants.DATE_FORMAT_MM_YYYY));
        responseDto.setCvv(creditCard.getCvv());
        responseDto.setCardType(creditCard.getCardType());
        responseDto.setIsActive(creditCard.getIsActive());
        return responseDto;
    }
}