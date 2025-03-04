package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.ProdcutImages;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.enums.ProductCatogeryEnums;
import com.artisan_market_place.repository.ProdcutImagesRepository;
import com.artisan_market_place.repository.ProductsRepositoryImpl;
import com.artisan_market_place.requestDto.ProdcutImagesRequestDto;
import com.artisan_market_place.requestDto.ProductRequestDto;
import com.artisan_market_place.requestDto.SearchProductRequestDto;
import com.artisan_market_place.responseDto.ProdcutImagesResponseDto;
import com.artisan_market_place.responseDto.ProdcutResponseDto;
import com.artisan_market_place.responseDto.SearchProductResponseDto;
import com.artisan_market_place.service.ProdcuctService;
import com.artisan_market_place.validators.ProductValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProdcuctService {
    private final ProductsRepositoryImpl productsRepository;
    private final ProductValidator productValidator;
    private final ProdcutImagesRepository productImagesRepository;

    public ProductServiceImpl(ProductsRepositoryImpl productsRepository, ProductValidator productValidator, ProdcutImagesRepository productImagesRepository) {
        this.productsRepository = productsRepository;
        this.productValidator = productValidator;
        this.productImagesRepository = productImagesRepository;
    }
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ProdcutResponseDto createProduct(ProductRequestDto dto, String loginUser) {
        productValidator.validateCreateProductRequest(dto);
        Products product = new Products();
        product = setProdct(product, dto);
        product.setCreatedBy(loginUser);
        productsRepository.save(product);
        List<ProdcutImages> productImages = setProductImages(product, dto.getImages());
        return getProdct(product,productImages);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ProdcutResponseDto updateProduct(ProductRequestDto dto, Long productId, String loginUser) {
        productValidator.validateUpdateProductRequest(productId,dto);
        Products product = productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product = setProdct(product, dto);
        productsRepository.save(product);
        List<ProdcutImages> productImages = productImagesRepository.findByProductId(productId);
        productImages.forEach(productImagesRepository::delete);
        List<ProdcutImages> updatedImages = setProductImages(product, dto.getImages());
        return getProdct(product,updatedImages);
    }

    @Override
    public ProdcutResponseDto getProductById(Long productId, String loginUser) {
        Products product = productValidator.validateProductIdAndReturn(productId);
        List<ProdcutImages> productImages = productImagesRepository.findByProductId(productId);
        return getProdct(product,productImages);
    }

    @Override
    public List<ProdcutResponseDto> getAllProducts(String loginUser) {
        List<Products> products = productsRepository.findAll();
        return products.stream()
                .map(product -> {
                    List<ProdcutImages> productImages = productImagesRepository.findByProductId(product.getProductId());
                    return getProdct(product, productImages);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public HashMap<String, String> deleteProduct(Long productId, String loginUser) {
        Products product = productValidator.validateProductIdAndReturn(productId);
        productsRepository.delete(product);
        List<ProdcutImages> productImages = productImagesRepository.findByProductId(productId);
        productImages.forEach(productImagesRepository::delete);
        HashMap<String, String> response = new HashMap<>();
        response.put("productId", productId.toString());
        response.put("status", "Product deleted successfully");
        return response;
    }

    @Override
    public List<ProdcutResponseDto> getProductsByCategory(Long userId, String category, String loginUser) {
        List<ProdcutResponseDto> response = new ArrayList<>();
        List<Products> products = productsRepository.findByUserIdAndCategory(userId, category);
        List<ProdcutImages> productImages = new ArrayList<>();
        if(!products.isEmpty()) {
        for (Products product : products) {
        productImages = productImagesRepository.findByProductId(product.getProductId());
        ProdcutResponseDto responseDto = new ProdcutResponseDto();
        responseDto = getProdct(product, productImages);
        response.add(responseDto);
        }}
        return response;
    }


    public List<SearchProductResponseDto> searchProducts(SearchProductRequestDto searchRequest, int pageSize, int pageNumber) {
        List<Object[]> products = productsRepository.searchByFilters(searchRequest, pageSize, pageNumber);
        List<SearchProductResponseDto> responseList = new ArrayList<>();
        for (Object[] product : products) {
            responseList.add(getSearchResponse(product));
        }
        return responseList;
    }

    public SearchProductResponseDto getSearchResponse(Object[] product) {
        SearchProductResponseDto response = new SearchProductResponseDto();
        response.setProductId(product[0] != null ? ((Number) product[0]).longValue() : null);
        response.setSellerId(product[1] != null ? ((Number) product[1]).longValue() : null);
        response.setSellerName(product[2] != null ? product[2].toString() : null);
        response.setDescription(product[3] != null ? product[3].toString() : null);
        response.setPrice(product[4] != null ? new BigDecimal(product[4].toString()) : null);
        response.setStockQuantity(product[5] != null ? ((Number) product[5]).intValue() : null);
        response.setCategory(product[6] != null ? product[6].toString() : null);
        response.setBrand(product[7] != null ? product[7].toString() : null);
        response.setWeight(product[8] != null ? (Double) product[8] : null);
        response.setDimensions(product[9] != null ? product[9].toString() : null);
        response.setStatus(product[10] != null ? product[10].toString() : null);
        response.setColor(product[11] != null ? product[11].toString() : null);
        response.setProductType(product[12] != null ? product[12].toString() : null);
        response.setSellerName(product[13] != null ? product[13].toString() : null);
        response.setSellerPhone(product[14] != null ? product[14].toString() : null);
        response.setSellerEmail(product[15] != null ? product[15].toString() : null);
        response.setSellerRating(product[16] != null ? (Double)(product[16]) : null);
        response.setProductRating(product[17] != null ? (Double)(product[17]) : null);
        response.setFlatDiscount(product[18] != null ? new BigDecimal(product[18].toString()) : null);
        response.setMaxDiscountAmount(product[19] != null ? new BigDecimal(product[19].toString()) : null);
        response.setMinPurchaseAmount(product[20] != null ? new BigDecimal(product[20].toString()) : null);
        response.setOfferStartTime(product[21] != null ? (Date) product[21] : null);
        response.setOfferEndTime(product[22] != null ? (Date) product[22] : null);
        response.setIsOffer(product[23] != null);
        return response;
    }



    private Products setProdct(Products product, ProductRequestDto dto) {
        if(dto == null) return null;
        product.setUserId(dto.getUserId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setTotalQuantity(dto.getTotalQuantity());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(ProductCatogeryEnums.valueOf(dto.getCategory()));
        product.setBrand(dto.getBrand());
        product.setWeight(dto.getWeight());
        product.setDimensions(dto.getDimensions());
        product.setStatus(dto.getStatus());
        product.setColor(dto.getColor());
        product.setProductType(dto.getProductType());
        return product;
    }

    private ProdcutResponseDto getProdct(Products product,List<ProdcutImages> images) {
        if(product == null) return null;
        ProdcutResponseDto response = new ProdcutResponseDto();
        response.setProductId(product.getProductId());
        response.setUserId(product.getUserId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setTotalQuantity(product.getTotalQuantity());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory().toString());
        response.setBrand(product.getBrand());
        response.setWeight(product.getWeight());
        response.setDimensions(product.getDimensions());
        response.setStatus(product.getStatus());
        response.setColor(product.getColor());
        response.setProductType(product.getProductType());
        getProductImages(product,images);
        return response;
    }

    private List<ProdcutImages> setProductImages(Products product, List<ProdcutImagesRequestDto> images) {
        if (images == null)  return Collections.emptyList();
        List<ProdcutImages> prodcutImage= new ArrayList<>();
        List<ProdcutImages> imageEntities = images.stream()
        .map(imgDto -> {
        ProdcutImages image = new ProdcutImages();
        image.setProductId(product.getProductId());
        image.setImageUrl(imgDto.getImageUrl());
        prodcutImage.add(image);
        return image;
        })
        .collect(Collectors.toList());
        productImagesRepository.saveAll(imageEntities);
        return prodcutImage;
    }

    private List<ProdcutImagesResponseDto> getProductImages(Products product, List<ProdcutImages> images) {
        if (images == null) return Collections.emptyList();
        return images.stream()
        .map(imgDto -> {
        ProdcutImagesResponseDto image = new ProdcutImagesResponseDto();
        image.setProductId(product.getProductId());
        image.setImageUrl(imgDto.getImageUrl());
        return image;
        })
        .collect(Collectors.toList());
    }
}