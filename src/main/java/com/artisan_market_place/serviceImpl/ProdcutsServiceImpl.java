package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.entity.ProdcutImages;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.repository.ProdcutImagesRepository;
import com.artisan_market_place.repository.ProductsRepository;
import com.artisan_market_place.requestDto.ProdcutImagesRequestDto;
import com.artisan_market_place.requestDto.ProductRequestDto;
import com.artisan_market_place.responseDto.ProdcutImagesResponseDto;
import com.artisan_market_place.responseDto.ProdcutResponseDto;
import com.artisan_market_place.service.ProdcutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdcutsServiceImpl implements ProdcutsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProdcutImagesRepository productImagesRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ProdcutResponseDto createProduct(ProductRequestDto dto, String loginUser) {
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
        Products product = productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
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
        productsRepository.deleteById(productId);
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

    private Products setProdct(Products product, ProductRequestDto dto) {
        product.setUserId(dto.getUserId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setTotalQuantity(dto.getTotalQuantity());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(dto.getCategory());
        product.setBrand(dto.getBrand());
        product.setWeight(dto.getWeight());
        product.setDimensions(dto.getDimensions());
        product.setStatus(dto.getStatus());
        product.setColor(dto.getColor());
        product.setProductType(dto.getProductType());
        return product;
    }

    private ProdcutResponseDto getProdct(Products product,List<ProdcutImages> images) {
        ProdcutResponseDto response = new ProdcutResponseDto();
        response.setProductId(product.getProductId());
        response.setUserId(product.getUserId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setTotalQuantity(product.getTotalQuantity());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
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