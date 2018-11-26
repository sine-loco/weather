package com.city.weather.services;

import com.city.weather.domain.dbos.CityDTO;
import com.city.weather.domain.entities.City;
import com.city.weather.domain.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

// TODO extract interface

/**
 * City service
 */
@Service
@Transactional
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public Mono<City> findCity(Mono<CityDTO> cityDtoMono) {
        final String name = this.getCityName(cityDtoMono);
        final City city = cityRepository.findByName(name);

        if (this.notNullCity(city) && city.getCityId() != null) {
            return just(city);
        }
        return empty();
    }

    private String getCityName(Mono<CityDTO> cityDtoMono) {
        // unfortunately JPA not reactive so far!
        return Objects.requireNonNull(cityDtoMono.block()).getName();
    }

    private boolean notNullCity(City city) {
        return city != null && city.getId() != null;
    }

}
