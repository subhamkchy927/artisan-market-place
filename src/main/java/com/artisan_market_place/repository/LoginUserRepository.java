package com.artisan_market_place.repository;

import com.artisan_market_place.entity.UsersLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginUserRepository extends JpaRepository<UsersLoginInfo,Long>,BaseRepository{
    UsersLoginInfo findByLoginId(String username);
}
