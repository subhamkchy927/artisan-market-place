package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.CreditCard;
import com.artisan_market_place.repository.CreditCardRepository;
import com.artisan_market_place.requestDto.CreditCardRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Optional;

@Service
public class CreditCardValidator {
    private final CreditCardRepository creditCardRepository;
    private final GlobalValidatorService globalValidatorService;

    public CreditCardValidator(CreditCardRepository creditCardRepository, GlobalValidatorService globalValidatorService) {
        this.creditCardRepository = creditCardRepository;
        this.globalValidatorService = globalValidatorService;
    }

    public void validateMandatory(CreditCardRequestDto card) {
        if (card == null) throw new ValidationException(MessageConstants.CARD_OBJECT_NULL);
        if(card.getUserId() == null) throw new ValidationException(MessageConstants.ASSOCIATED_USER_ID_MANDATORY);
        if (!StringUtils.hasText(card.getCardHolderName())) throw new ValidationException(MessageConstants.CARD_HOLDER_NAME_MANDATORY);
        if (!StringUtils.hasText(card.getCardNumber())) throw new ValidationException(MessageConstants.CARD_NUMBER_MANDATORY);
        if (card.getExpiryDate() == null) throw new ValidationException(MessageConstants.EXPIRY_DATE_MANDATORY);
        if (!StringUtils.hasText(card.getCvv())) throw new ValidationException(MessageConstants.CVV_MANDATORY);
        if (card.getCardType() == null) throw new ValidationException(MessageConstants.CARD_TYPE_MANDATORY);
    }

    public void validateCreateCardRequest(CreditCardRequestDto card) {
        validateMandatory(card);
        isValidCard(card);
        globalValidatorService.validateUserId(card.getUserId());
        if (creditCardRepository.existsByCardNumberAndUserId(card.getCardNumber(),card.getUserId())) throw new ValidationException(MessageConstants.CARD_NUMBER_ALREADY_EXISTS);
    }

    public void validateUpdateCardRequest(Long cardId, CreditCardRequestDto card) {
        validateMandatory(card);
        isValidCard(card);
        globalValidatorService.validateUserId(card.getUserId());
        if (creditCardRepository.existsByCardNumberAndUserIdAndCardIdNot(card.getCardNumber(),card.getUserId(), cardId)) throw new ValidationException(MessageConstants.CARD_NUMBER_ALREADY_EXISTS);
    }
    private void isValidCard(CreditCardRequestDto card) {
        if (card.getCardNumber().length() < 14 || card.getCardNumber().length() > 16) throw new ValidationException(MessageConstants.INVALID_CARD_NUMBER_LENGTH);
        if (card.getCvv().length() < 3 || card.getCvv().length() > 4) throw new ValidationException(MessageConstants.INVALID_CVV_LENGTH);
    }
    public CreditCard validateCardIdAndReturn(Long cardId) {
        Optional<CreditCard> cardOpt = creditCardRepository.findById(cardId);
        if (!cardOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.CARD_NOT_FOUND);
        return cardOpt.get();
    }
}
