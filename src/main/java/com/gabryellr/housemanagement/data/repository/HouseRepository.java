package com.gabryellr.housemanagement.data.repository;

import com.gabryellr.housemanagement.data.model.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {

    Boolean existsByStreetAndZipCodeAndNumberAndCity(String street, String zipCode, Integer number, String city);

}