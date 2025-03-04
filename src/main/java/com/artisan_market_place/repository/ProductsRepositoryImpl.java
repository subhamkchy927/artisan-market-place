package com.artisan_market_place.repository;
import com.artisan_market_place.entity.Products;
import com.artisan_market_place.requestDto.SearchProductRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ProductsRepositoryImpl extends JpaRepository<Products,Long>,BaseRepository {

    @Query("SELECT p FROM Products p WHERE (:userId IS NULL OR p.userId = :userId) AND (:category IS NULL OR p.category = :category)")
    List<Products> findByUserIdAndCategory(@Param("userId") Long userId, @Param("category") String category);
    boolean existsByNameAndCategoryAndBrandAndWeightAndDimensionsAndColorAndUserId(String name, String category, String brand, Double weight, String dimensions, String color, Long userId);
    boolean existsByNameAndCategoryAndBrandAndWeightAndDimensionsAndColorAndUserIdAndProductIdNot(String name, String category, String brand, Double weight, String dimensions, String color, Long userId, Long productId);

    public default List<Object[]> searchByFilters(SearchProductRequestDto searchRequest, int pageSize, int pageNumber) {
        StringBuilder queryBuilder = new StringBuilder();
        List<Object> params = new ArrayList<>();
        queryBuilder.append("SELECT p.product_id, p.user_id, p.name, p.description, p.price, p.stock_quantity, p.category, p.brand, p.weight, p.dimensions, p.status, p.color, p.product_type, ")
        .append("u.first_name || ' ' || u.last_name AS seller_name, u.phone_number AS seller_phone, u.email AS seller_email, u.rating AS seller_rating, r.rating AS product_rating, ")
        .append("o.flat_discount, o.max_discount_amount, o.min_purchase_amount, o.start_date AS offer_start_time, o.end_date AS offer_end_time, o.offer_id ")
        .append("FROM products p ")
        .append("JOIN users u ON p.user_id = u.user_id ")
        .append("LEFT JOIN product_offers o ON p.product_id = o.product_id ")
        .append("LEFT JOIN product_reviews r ON p.product_id = r.prodcut_id WHERE 1=1 ");

        if (searchRequest.getSellerId() != null) {
            queryBuilder.append(" AND p.user_id = ? ");
            params.add(searchRequest.getSellerId());
        }

        if (searchRequest.getCompanyName() != null) {
            queryBuilder.append(" AND u.company_name = ? ");
            params.add(searchRequest.getCompanyName());
        }

        if (searchRequest.getProductName() != null) {
            queryBuilder.append(" AND p.name LIKE ? ");
            params.add("%" + searchRequest.getProductName() + "%");
        }

        if (searchRequest.getLowerPrice() != null && searchRequest.getHigherPrice() != null) {
            queryBuilder.append(" AND p.price BETWEEN ? AND ? ");
            params.add(searchRequest.getLowerPrice());
            params.add(searchRequest.getHigherPrice());
        }

        if (searchRequest.getCategory() != null) {
            queryBuilder.append(" AND p.category = ? ");
            params.add(searchRequest.getCategory());
        }

        if (searchRequest.getBrand() != null) {
            queryBuilder.append(" AND p.brand = ? ");
            params.add(searchRequest.getBrand());
        }

        if (searchRequest.getWeight() != null) {
            queryBuilder.append(" AND p.weight = ? ");
            params.add(searchRequest.getWeight());
        }

        if (searchRequest.getColor() != null) {
            queryBuilder.append(" AND p.color = ? ");
            params.add(searchRequest.getColor());
        }
        String query = queryBuilder.toString();
        return findUsingNativeSQLQuery(query, params, pageSize, pageNumber);
    }


}
