package com.artisan_market_place.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "product_images")
@Entity
public class ProdcutImages extends BaseEntity{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "image_id")
        private Long imageId;

        @Column(name = "product_id", nullable = false)
        private Long productId;

        @Column(name = "image_url", nullable = false, length = 500)
        private String imageUrl;
    }

