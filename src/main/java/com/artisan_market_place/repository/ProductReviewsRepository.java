package com.artisan_market_place.repository;

import com.artisan_market_place.entity.ProdcutReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewsRepository extends JpaRepository<ProdcutReviews, Long> {
    
    List<ProdcutReviews> findByProdcutId(Long productId);
    
    @Query("SELECT AVG(r.rating) FROM ProdcutReviews r WHERE r.prodcutId = :productId")
    Double getAverageRatingByProductId(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(r) FROM ProdcutReviews r WHERE r.prodcutId = :productId")
    Long getReviewCountByProductId(@Param("productId") Long productId);
    
    @Query("SELECT r FROM ProdcutReviews r WHERE r.prodcutId = :productId ORDER BY r.reviewId DESC")
    List<ProdcutReviews> findByProdcutIdOrderByReviewIdDesc(@Param("productId") Long productId);
}

