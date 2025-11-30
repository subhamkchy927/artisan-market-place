package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.ProductOffers;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.repository.ProductOffersRepository;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.requestDto.ProductOfferRequestDto;
import com.artisan_market_place.responseDto.ProductOfferResponseDto;
import com.artisan_market_place.service.ProductOfferService;
import com.artisan_market_place.utils.DateTimeUtil;
import com.artisan_market_place.validators.ProductOfferValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductOfferServiceImpl implements ProductOfferService {

    private final ProductOffersRepository productOffersRepository;
    private final ProductOfferValidator productOfferValidator;
    private final ProductsRepositoryImpl productsRepository;

    public ProductOfferServiceImpl(ProductOffersRepository productOffersRepository, ProductOfferValidator productOfferValidator, ProductsRepositoryImpl productsRepository) {
        this.productOffersRepository = productOffersRepository;
        this.productOfferValidator = productOfferValidator;
        this.productsRepository = productsRepository;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ProductOfferResponseDto createOffer(ProductOfferRequestDto dto, String loginUser) {
        productOfferValidator.validateCreateOfferRequest(dto);
        
        ProductOffers offer = new ProductOffers();
        offer.setProductId(dto.getProductId());
        offer.setCustomerType(dto.getCustomerType());
        offer.setOfferName(dto.getOfferName());
        offer.setOfferDescription(dto.getOfferDescription());
        offer.setPercentageDiscount(dto.getPercentageDiscount());
        offer.setFlatDiscount(dto.getFlatDiscount());
        offer.setMaxDiscountAmount(dto.getMaxDiscountAmount());
        offer.setMinPurchaseAmount(dto.getMinPurchaseAmount());
        offer.setStartTime(dto.getStartTime());
        offer.setEndTime(dto.getEndTime());
        offer.setOfferStatus(dto.getOfferStatus() != null ? dto.getOfferStatus() : "ACTIVE");
        offer.setAuditInfo(loginUser);
        
        productOffersRepository.save(offer);
        return getOfferResponse(offer);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ProductOfferResponseDto updateOffer(ProductOfferRequestDto dto, Long offerId, String loginUser) {
        productOfferValidator.validateUpdateOfferRequest(offerId, dto);
        
        ProductOffers offer = productOffersRepository.findById(offerId).orElseThrow();
        
        if (dto.getProductId() != null) offer.setProductId(dto.getProductId());
        if (dto.getCustomerType() != null) offer.setCustomerType(dto.getCustomerType());
        if (dto.getOfferName() != null) offer.setOfferName(dto.getOfferName());
        if (dto.getOfferDescription() != null) offer.setOfferDescription(dto.getOfferDescription());
        if (dto.getPercentageDiscount() != null) offer.setPercentageDiscount(dto.getPercentageDiscount());
        if (dto.getFlatDiscount() != null) offer.setFlatDiscount(dto.getFlatDiscount());
        if (dto.getMaxDiscountAmount() != null) offer.setMaxDiscountAmount(dto.getMaxDiscountAmount());
        if (dto.getMinPurchaseAmount() != null) offer.setMinPurchaseAmount(dto.getMinPurchaseAmount());
        if (dto.getStartTime() != null) offer.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) offer.setEndTime(dto.getEndTime());
        if (dto.getOfferStatus() != null) offer.setOfferStatus(dto.getOfferStatus());
        
        offer.setAuditInfo(loginUser);
        productOffersRepository.save(offer);
        return getOfferResponse(offer);
    }

    @Override
    public ProductOfferResponseDto getOfferById(Long offerId, String loginUser) {
        ProductOffers offer = productOfferValidator.validateOfferIdAndReturn(offerId);
        return getOfferResponse(offer);
    }

    @Override
    public List<ProductOfferResponseDto> getAllOffers(String loginUser) {
        List<ProductOffers> offers = productOffersRepository.findAll();
        return offers.stream()
                .map(this::getOfferResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOfferResponseDto> getOffersByProductId(Long productId, String loginUser) {
        List<ProductOffers> offers = productOffersRepository.findByProductId(productId);
        return offers.stream()
                .map(this::getOfferResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOfferResponseDto> getActiveOffers(String loginUser) {
        Date currentDate = DateTimeUtil.getCurrentUTCDate();
        List<ProductOffers> offers = productOffersRepository.findActiveOffers(currentDate);
        return offers.stream()
                .map(this::getOfferResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOfferResponseDto> getActiveOffersByProductId(Long productId, String loginUser) {
        Date currentDate = DateTimeUtil.getCurrentUTCDate();
        List<ProductOffers> offers = productOffersRepository.findActiveOffersByProductId(productId, currentDate);
        return offers.stream()
                .map(this::getOfferResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public HashMap<String, String> deleteOffer(Long offerId, String loginUser) {
        ProductOffers offer = productOfferValidator.validateOfferIdAndReturn(offerId);
        productOffersRepository.delete(offer);
        
        HashMap<String, String> response = new HashMap<>();
        response.put("offerId", offerId.toString());
        response.put("status", "Offer deleted successfully");
        return response;
    }

    private ProductOfferResponseDto getOfferResponse(ProductOffers offer) {
        if (offer == null) return null;
        
        ProductOfferResponseDto response = new ProductOfferResponseDto();
        response.setOfferId(offer.getOfferId());
        response.setProductId(offer.getProductId());
        response.setCustomerType(offer.getCustomerType());
        response.setOfferName(offer.getOfferName());
        response.setOfferDescription(offer.getOfferDescription());
        response.setPercentageDiscount(offer.getPercentageDiscount());
        response.setFlatDiscount(offer.getFlatDiscount());
        response.setMaxDiscountAmount(offer.getMaxDiscountAmount());
        response.setMinPurchaseAmount(offer.getMinPurchaseAmount());
        response.setStartTime(offer.getStartTime());
        response.setEndTime(offer.getEndTime());
        response.setOfferStatus(offer.getOfferStatus());
        response.setCreationDate(offer.getCreationDate());
        response.setLastUpdateDate(offer.getLastUpdateDate());
        
        if (offer.getProductId() != null) {
            Optional<Products> productOpt = productsRepository.findById(offer.getProductId());
            if (productOpt.isPresent()) {
                response.setProductName(productOpt.get().getName());
            }
        }
        
        return response;
    }
}

