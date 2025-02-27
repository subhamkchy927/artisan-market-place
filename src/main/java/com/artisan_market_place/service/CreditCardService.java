package com.artisan_market_place.service;

import com.artisan_market_place.requestDto.CreditCardRequestDto;
import com.artisan_market_place.responseDto.CreditCardResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CreditCardService {
    CreditCardResponseDto addCreditCard(CreditCardRequestDto dto, String loginUser);
    CreditCardResponseDto updateCreditCard(CreditCardRequestDto dto, Long cardId, String loginUser);
    CreditCardResponseDto getCreditCardById(Long cardId, String loginUser);
    List<CreditCardResponseDto> getAllCreditCards(Long userId, String loginUser);
    Map<String, String> deleteCreditCard(Long cardId, String loginUser);
}
