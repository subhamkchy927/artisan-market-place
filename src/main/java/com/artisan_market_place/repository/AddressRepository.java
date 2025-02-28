package com.artisan_market_place.repository;
import com.artisan_market_place.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>, BaseRepository{

}
