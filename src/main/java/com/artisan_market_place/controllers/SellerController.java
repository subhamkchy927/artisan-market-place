package com.artisan_market_place.controllers;

import com.artisan_market_place.requestDto.SellerRequestDto;
import com.artisan_market_place.responseDto.SellerResponseDto;
import com.artisan_market_place.serviceImpl.SellerDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/seller")
@Slf4j
public class SellerController {

    @Autowired
    SellerDetailsServiceImpl sellerService;

        @PostMapping("/")
        public ResponseEntity<SellerResponseDto> createSeller(@RequestBody SellerRequestDto dto) {
            log.info("Request from client for seller creation"+dto);
            SellerResponseDto response = sellerService.createSeller(dto);
            return ResponseEntity.ok(response);
        }




    }

