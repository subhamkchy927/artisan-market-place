package com.artisan_market_place.validators;

import com.artisan_market_place.Exception.ResourceNotFoundException;
import com.artisan_market_place.Exception.ValidationException;
import com.artisan_market_place.constants.MessageConstants;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.entity.Users;
import com.artisan_market_place.enums.UserRolesEnums;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.requestDto.ProductRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductValidator {

    private final ProductsRepositoryImpl productsRepository;
    private final GlobalValidator globalValidator;

    public ProductValidator(ProductsRepositoryImpl productsRepository, GlobalValidator globalValidator) {
        this.productsRepository = productsRepository;
        this.globalValidator = globalValidator;
    }

    public void validateMandatory(ProductRequestDto product) {
        if (product == null) throw new ValidationException(MessageConstants.PRODUCT_OBJECT_NULL);
        if (product.getUserId() == null) throw new ValidationException(MessageConstants.SELLER_ID_MANDATORY);
        if (!StringUtils.hasText(product.getName())) throw new ValidationException(MessageConstants.PRODUCT_NAME_MANDATORY);
        if (!StringUtils.hasText(product.getCategory())) throw new ValidationException(MessageConstants.PRODUCT_CATEGORY_MANDATORY);
        if (!StringUtils.hasText(product.getBrand())) throw new ValidationException(MessageConstants.PRODUCT_BRAND_MANDATORY);
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException(MessageConstants.PRODUCT_PRICE_INVALID);
        if (product.getTotalQuantity() == null || product.getTotalQuantity() < 0) throw new ValidationException(MessageConstants.PRODUCT_QUANTITY_INVALID);
    }

    public void validateCreateProductRequest(ProductRequestDto product) {
        validateMandatory(product);
        validateUser(product.getUserId());
        boolean exists = productsRepository.existsByNameAndCategoryAndBrandAndWeightAndDimensionsAndColorAndUserId(product.getName(), product.getCategory(), product.getBrand(), product.getWeight(), product.getDimensions(), product.getColor(), product.getUserId());
        if (exists) throw new ValidationException(MessageConstants.PRODUCT_ALREADY_EXISTS);
    }

    public void validateUpdateProductRequest(Long productId, ProductRequestDto product) {
        validateMandatory(product);
        validateUser(product.getUserId());
        boolean exists = productsRepository.existsByNameAndCategoryAndBrandAndWeightAndDimensionsAndColorAndUserIdAndProductIdNot(product.getName(), product.getCategory(), product.getBrand(), product.getWeight(), product.getDimensions(), product.getColor(), product.getUserId(), productId);
        if (exists) throw new ValidationException(MessageConstants.PRODUCT_ALREADY_EXISTS);
    }

     public void validateUser(Long userId){
         Users user = globalValidator.validateUserIdAndReturn(userId);
         if(UserRolesEnums.ADMIN.compareTo(user.getRole()) != 0 && UserRolesEnums.SELLER.compareTo(user.getRole()) != 0 && UserRolesEnums.SELLER_CUSTOMER.compareTo(user.getRole()) != 0) throw new ValidationException(MessageConstants.ONLY_ADMIN_SELLER_CAN_ADD_UPDATE_PRODUCT);
     }

    public Products validateProductIdAndReturn(Long productId) {
        Optional<Products> productOpt = productsRepository.findById(productId);
        if (!productOpt.isPresent()) throw new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND);
        return productOpt.get();
    }
}

