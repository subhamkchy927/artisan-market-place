package com.artisan_market_place.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Table(name = "prodcuts")
@Entity
public class Products extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "user_id", nullable = false, unique = true, length = 255)
    private String userId;

    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "dimensions", length = 50)
    private String dimensions;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "color", nullable = false, length = 32)
    private String color;

    @Column(name = "product_type", nullable = false, length = 50)
    private String productType;
}
