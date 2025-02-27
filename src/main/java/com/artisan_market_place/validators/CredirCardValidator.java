package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.ApplicationConstants;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.CreditCard;
import com.artisan_market_place.repository.CreditCardRepository;
import com.artisan_market_place.requestDto.CreditCardRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CredirCardValidator {
    private final CreditCardRepository creditCardRepository;

    public CredirCardValidator(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public void validateMandatory(CreditCardRequestDto card) {
        if (card == null) throw new ValidationException(MessageConstants.CARD_OBJECT_NULL);
        if (!StringUtils.hasText(card.getCardHolderName())) throw new ValidationException(MessageConstants.CARD_HOLDER_NAME_MANDATORY);
        if (!StringUtils.hasText(card.getCardNumber())) throw new ValidationException(MessageConstants.CARD_NUMBER_MANDATORY);
        if (card.getExpiryDate() == null) throw new ValidationException(MessageConstants.EXPIRY_DATE_MANDATORY);
        if (!StringUtils.hasText(card.getCvv())) throw new ValidationException(MessageConstants.CVV_MANDATORY);
        if (card.getCardType() == null) throw new ValidationException(MessageConstants.CARD_TYPE_MANDATORY);
    }

    public void validateCreateCardRequest(CreditCardRequestDto card) {
        if (creditCardRepository.existsByCardNumber(card.getCardNumber()))
            throw new ValidationException(MessageConstants.CARD_NUMBER_ALREADY_EXISTS);
        if (!isValidCardNumber(card.getCardNumber()))
            throw new ValidationException(MessageConstants.INVALID_CARD_NUMBER);
        if (!isValidCvv(card.getCvv()))
            throw new ValidationException(MessageConstants.INVALID_CVV);
    }

    public void validateUpdateCardRequest(Long cardId, CreditCardRequestDto card) {
        if (creditCardRepository.existsByCardNumberAndCardIdNot(card.getCardNumber(), cardId))
            throw new ValidationException(MessageConstants.CARD_NUMBER_ALREADY_EXISTS);
        if (!isValidCardNumber(card.getCardNumber()))
            throw new ValidationException(MessageConstants.INVALID_CARD_NUMBER);
        if (!isValidCvv(card.getCvv()))
            throw new ValidationException(MessageConstants.INVALID_CVV);
    }

    public CreditCard validateCardIdAndReturn(Long cardId) {
        Optional<CreditCard> cardOpt = creditCardRepository.findById(cardId);
        if (!cardOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.CARD_NOT_FOUND);
        return cardOpt.get();
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches(ApplicationConstants.CARD_NUMBER_PATTERN);
    }

    private boolean isValidCvv(String cvv) {
        return cvv != null && cvv.matches(ApplicationConstants.CVV_PATTERN);
    }
}
