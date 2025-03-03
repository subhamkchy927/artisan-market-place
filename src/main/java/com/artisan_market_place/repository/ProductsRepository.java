package com.artisan_market_place.repository;

import com.artisan_market_place.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {

        @Query("SELECT p FROM Products p " +
                "WHERE (:userId IS NULL OR p.userId = :userId) " +
                "AND (:category IS NULL OR p.category = :category)")
        List<Products> findByUserIdAndCategory(@Param("userId") Long userId, @Param("category") String category);

}
