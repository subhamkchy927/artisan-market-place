package com.artisan_market_place.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/test")
public class TestController {

    @GetMapping("/")
    public String test(){
        return "Test is successful";
    }
}
