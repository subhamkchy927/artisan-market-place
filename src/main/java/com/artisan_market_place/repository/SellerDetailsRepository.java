package com.artisan_market_place.repository;

import com.artisan_market_place.entity.SellerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerDetailsRepository extends JpaRepository<SellerDetails, Long> {

}
