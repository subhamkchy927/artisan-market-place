package com.artisan_market_place.repository;

import com.artisan_market_place.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findByEmail(String username);
}
