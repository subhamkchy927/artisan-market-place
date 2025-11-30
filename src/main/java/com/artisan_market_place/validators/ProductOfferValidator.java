package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.ProductOffers;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.repository.ProductOffersRepository;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.requestDto.ProductOfferRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class ProductOfferValidator {

    private final ProductOffersRepository productOffersRepository;
    private final ProductsRepositoryImpl productsRepository;
    private final GlobalValidator globalValidator;

    public ProductOfferValidator(ProductOffersRepository productOffersRepository, ProductsRepositoryImpl productsRepository, GlobalValidator globalValidator) {
        this.productOffersRepository = productOffersRepository;
        this.productsRepository = productsRepository;
        this.globalValidator = globalValidator;
    }

    public void validateCreateOfferRequest(ProductOfferRequestDto dto) {
        if (dto == null) throw new ValidationException(MessageConstants.OFFER_OBJECT_NULL);
        if (!StringUtils.hasText(dto.getOfferName())) throw new ValidationException(MessageConstants.OFFER_NAME_MANDATORY);
        if (!StringUtils.hasText(dto.getOfferDescription())) throw new ValidationException(MessageConstants.OFFER_DESCRIPTION_MANDATORY);
        if (dto.getStartTime() == null) throw new ValidationException(MessageConstants.OFFER_START_DATE_MANDATORY);
        if (dto.getEndTime() == null) throw new ValidationException(MessageConstants.OFFER_END_DATE_MANDATORY);
        
        if (dto.getEndTime().before(dto.getStartTime())) {
            throw new ValidationException(MessageConstants.OFFER_END_DATE_BEFORE_START);
        }
        
        if (dto.getPercentageDiscount() != null) {
            if (dto.getPercentageDiscount() < 0 || dto.getPercentageDiscount() > 100) {
                throw new ValidationException(MessageConstants.INVALID_PERCENTAGE_DISCOUNT);
            }
        }
        
        if (dto.getFlatDiscount() != null && dto.getFlatDiscount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(MessageConstants.INVALID_DISCOUNT_AMOUNT);
        }
        
        if (dto.getProductId() != null) {
            validateProductId(dto.getProductId());
        }
    }

    public void validateUpdateOfferRequest(Long offerId, ProductOfferRequestDto dto) {
        if (dto == null) throw new ValidationException(MessageConstants.OFFER_OBJECT_NULL);
        validateOfferId(offerId);
        
        if (dto.getStartTime() != null && dto.getEndTime() != null) {
            if (dto.getEndTime().before(dto.getStartTime())) {
                throw new ValidationException(MessageConstants.OFFER_END_DATE_BEFORE_START);
            }
        }
        
        if (dto.getPercentageDiscount() != null) {
            if (dto.getPercentageDiscount() < 0 || dto.getPercentageDiscount() > 100) {
                throw new ValidationException(MessageConstants.INVALID_PERCENTAGE_DISCOUNT);
            }
        }
        
        if (dto.getFlatDiscount() != null && dto.getFlatDiscount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(MessageConstants.INVALID_DISCOUNT_AMOUNT);
        }
    }

    public ProductOffers validateOfferIdAndReturn(Long offerId) {
        Optional<ProductOffers> offerOpt = productOffersRepository.findById(offerId);
        if (!offerOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.OFFER_NOT_FOUND);
        return offerOpt.get();
    }

    public void validateOfferId(Long offerId) {
        Optional<ProductOffers> offerOpt = productOffersRepository.findById(offerId);
        if (!offerOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.OFFER_NOT_FOUND);
    }

    public void validateProductId(Long productId) {
        Optional<Products> productOpt = productsRepository.findById(productId);
        if (!productOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND);
    }
}

