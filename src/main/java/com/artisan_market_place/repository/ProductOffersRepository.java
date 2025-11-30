package com.artisan_market_place.repository;

import com.artisan_market_place.entity.ProductOffers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductOffersRepository extends JpaRepository<ProductOffers, Long> {
    
    List<ProductOffers> findByProductId(Long productId);
    
    @Query("SELECT o FROM ProductOffers o WHERE o.productId = :productId AND o.offerStatus = :status")
    List<ProductOffers> findByProductIdAndOfferStatus(@Param("productId") Long productId, @Param("status") String status);
    
    @Query("SELECT o FROM ProductOffers o WHERE o.startTime <= :currentDate AND o.endTime >= :currentDate AND o.offerStatus = 'ACTIVE'")
    List<ProductOffers> findActiveOffers(@Param("currentDate") Date currentDate);
    
    @Query("SELECT o FROM ProductOffers o WHERE o.productId = :productId AND o.startTime <= :currentDate AND o.endTime >= :currentDate AND o.offerStatus = 'ACTIVE'")
    List<ProductOffers> findActiveOffersByProductId(@Param("productId") Long productId, @Param("currentDate") Date currentDate);
}

