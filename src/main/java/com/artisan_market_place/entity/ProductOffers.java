package com.artisan_market_place.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "product_offers")
public class ProductOffers extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long offerId;

    @Column(name = "product_id", nullable = true)
    private Long productId;

    @Column(name = "customer_type", nullable = true)
    private String customerType;

    @Column(name = "offer_name", nullable = false)
    private String offerName;

    @Column(name = "offer_description", nullable = false)
    private String offerDescription;

    @Column(name = "percentage_discount", nullable = true)
    private Double percentageDiscount;

    @Column(name = "flat_discount", nullable = true)
    private BigDecimal flatDiscount;

    @Column(name = "max_discount_amount", nullable = true)
    private BigDecimal maxDiscountAmount;

    @Column(name = "min_purchase_amount", nullable = true)
    private BigDecimal minPurchaseAmount;

    @Column(name = "start_date", nullable = false)
    private Date startTime;

    @Column(name = "end_date", nullable = false)
    private Date endTime;

    @Column(name = "offer_status", nullable = false)
    private String offerStatus;
}
