package com.artisan_market_place.repository;

import com.artisan_market_place.entity.ProdcutImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdcutImagesRepository extends JpaRepository<ProdcutImages,Long> {
    List<ProdcutImages> findByProductId(Long productId);
}
