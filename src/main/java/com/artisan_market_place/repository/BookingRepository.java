package com.artisan_market_place.repository;

import com.artisan_market_place.entity.Booking;
import com.artisan_market_place.enums.BookingStatusEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByCustomerId(Long customerId);
    
    List<Booking> findBySellerId(Long sellerId);
    
    List<Booking> findByProductId(Long productId);
    
    List<Booking> findByDeliveryPersonId(Long deliveryPersonId);
    
    List<Booking> findByStatus(BookingStatusEnums status);
    
    @Query("SELECT b FROM Booking b WHERE b.customerId = :customerId AND b.status = :status")
    List<Booking> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") BookingStatusEnums status);
    
    @Query("SELECT b FROM Booking b WHERE b.sellerId = :sellerId AND b.status = :status")
    List<Booking> findBySellerIdAndStatus(@Param("sellerId") Long sellerId, @Param("status") BookingStatusEnums status);
    
    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate")
    List<Booking> findByBookingDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT b FROM Booking b WHERE b.productId = :productId AND b.customerId = :customerId AND b.status IN :statuses")
    List<Booking> findByProductIdAndCustomerIdAndStatusIn(@Param("productId") Long productId, @Param("customerId") Long customerId, @Param("statuses") List<BookingStatusEnums> statuses);
}

