package com.artisan_market_place.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "product_reviews")
@Entity
public class ProdcutReviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "prodcut_id")
    private Long prodcutId;

    @Column(name = "reviews", nullable = false)
    private String reviews;

    @Column(name = "reviews_count", nullable = false)
    private Integer reviewsCount;

    @Column(name = "ratings", nullable = false)
    private Double rating;

    @Column(name = "ratings_count", nullable = false)
    private Double ratingCount;
}
