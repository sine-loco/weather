package com.city.weather.services;

import com.city.weather.domain.dbos.CityDTO;
import com.city.weather.domain.dbos.CityWeatherDTO;
import com.city.weather.domain.entities.City;
import com.city.weather.domain.entities.Weather;
import com.city.weather.domain.repositories.CityRepository;
import com.city.weather.utl.exeptions.CityExistException;
import com.city.weather.web.api.OpenWeatherApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

// TODO extract interface

/**
 * City service
 *
 * unfortunately JPA not reactive so far! Coming soon ...
 */
@Service
@Transactional
@RequiredArgsConstructor // sine-loco. by the way, one does not need @Autowired on constructor :)
public class CityService {

    private final CityRepository cityRepository;
    private final OpenWeatherApi openWeatherApi;

    @Transactional(readOnly = true)
    public Mono<City> findCity(Mono<CityDTO> cityDtoMono) {
        String name = this.getCityName(cityDtoMono);
        City city = cityRepository.findByName(name);

        if (this.notNullCity(city) && city.getCityId() != null) {
            return just(city);
        }
        return empty();
    }

    public Mono<City> addCity(Mono<CityDTO> cityDtoMono) {
        String name = getCityName(cityDtoMono);
        City city = new City(name);
        if (cityRepository.existsCityByName(name)) {
            // sine-loco. it would be good for you to add msg with data of the city
            // you've just tried to add :)
            // or you can return Mono.error
            throw new CityExistException();
        }
        City savedCity = cityRepository.save(city);
        if (this.notNullCity(savedCity)) {
            return just(savedCity);
        }
        return empty();
    }

    private String getCityName(Mono<CityDTO> cityDtoMono) {
        // unfortunately JPA not reactive so far!
        return Objects.requireNonNull(cityDtoMono.block()).getName();
    }

    /* sine-loco. very quirky naming. I would name it 'is(City)' to show you know more than java :)
    * or at least 'isNotNull(City)' if you want to be strict java guy :) */
    private boolean notNullCity(City city) {
        return city != null && city.getId() != null;
    }

    @Transactional(readOnly = true)
    public Flux<City> allCities() {
        return Flux.fromIterable(cityRepository.findAll());
    }

    /* sine-loco
      why do you use Mono on input?
     */
    public void updateCity(City city, Mono<CityWeatherDTO> cityWeatherDto) {
        final CityWeatherDTO cityWeather = cityWeatherDto.block();
        final Long cityId = cityWeather.getCityId();
        if (cityId != null) {
            city.setCityId(cityId);
            city.setWeather(new Weather(cityWeather.getJson(), cityWeather.getXml()));
            cityRepository.save(city);
        }
        /* sine-loco. what about fail-fast? If cityId is null either throw or log.warn
        if null in cityId is acceptable - user MUST see that smthn gone wrong :) */
    }

    // TODO catch Unexpected error
    public void updateCities() {
        final Flux<City> fluxCityCities = this.allCities();
        fluxCityCities.subscribe(city -> {
            this.updateCity(city, openWeatherApi.inCityMedias(city.getName()));
        });
    }}
