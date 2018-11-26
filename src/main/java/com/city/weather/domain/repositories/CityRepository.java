package com.city.weather.domain.repositories;

import com.city.weather.domain.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCityId(Long id);

    City findByName(String name);

    boolean existsCityByName(String city);

}