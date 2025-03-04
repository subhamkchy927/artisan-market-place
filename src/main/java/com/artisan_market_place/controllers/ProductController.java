package com.artisan_market_place.controllers;

import com.artisan_market_place.Security.JwtUtil;
import com.artisan_market_place.requestDto.ProductRequestDto;
import com.artisan_market_place.responseDto.ProdcutResponseDto;
import com.artisan_market_place.serviceImpl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v2/product")
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;
    private final JwtUtil jwtUtil;

    public ProductController(ProductServiceImpl productService, JwtUtil jwtUtil) {
        this.productService = productService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public ResponseEntity<ProdcutResponseDto> createProduct(
            @RequestBody ProductRequestDto dto,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Creating product: {}", dto.getName());
        String loginUser = jwtUtil.extractUsername(token);
        ProdcutResponseDto response = productService.createProduct(dto, loginUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProdcutResponseDto> updateProduct(
            @RequestBody ProductRequestDto dto,
            @PathVariable Long productId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Updating product with ID: {}", productId);
        String loginUser = jwtUtil.extractUsername(token);
        ProdcutResponseDto response = productService.updateProduct(dto, productId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProdcutResponseDto> getProductById(
            @PathVariable Long productId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching product with ID: {}", productId);
        String loginUser = jwtUtil.extractUsername(token);
        ProdcutResponseDto response = productService.getProductById(productId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdcutResponseDto>> getAllProducts(
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching all products");
        String loginUser = jwtUtil.extractUsername(token);
        List<ProdcutResponseDto> response = productService.getAllProducts(loginUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<HashMap<String, String>> deleteProduct(
            @PathVariable Long productId,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Deleting product with ID: {}", productId);
        String loginUser = jwtUtil.extractUsername(token);
        HashMap<String, String> response = productService.deleteProduct(productId, loginUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProdcutResponseDto>> getProductsByCategory(
            @RequestParam (name = "userId",required = false) Long userId,
            @RequestParam (name = "category") String category,
            @RequestHeader(value = "Authorization") String token) {
        log.info("Fetching products for user ID: {} in category: {}", userId, category);
        String loginUser = jwtUtil.extractUsername(token);
        List<ProdcutResponseDto> response = productService.getProductsByCategory(userId, category, loginUser);
        return ResponseEntity.ok(response);
    }
}
