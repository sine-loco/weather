package com.city.weather.services;

import com.city.weather.domain.dbos.CityDTO;
import com.city.weather.domain.dbos.CityWeatherDTO;
import com.city.weather.domain.entities.City;
import com.city.weather.domain.entities.Weather;
import com.city.weather.domain.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/* sine-loco
as you use test-ng, you shoul create test suite. and make service test run after
repository test because service depends on repository */

@SpringBootTest
@Transactional
public class CityServiceTest extends AbstractTestNGSpringContextTests {

    private static final String MOSCOW = "Moscow";
    private static final String LONDON = "London";

    @DataProvider
    private static Object[][] getCity() {
        return new Object[][]{
                {10L, LONDON},
                {20L, MOSCOW}
        };
    }

    @Autowired
    CityService cityService;

    @Autowired
    CityRepository cityRepository;

    @Test(dataProvider = "getCity")
    public void testAddCity(Long cityId, String name) {
        final CityDTO cityDTO = new CityDTO(name);
        final Mono<City> cityMono = cityService.addCity(Mono.just(cityDTO));
        final City city = cityMono.block();

        assertNotNull( city );
        assertThat(city.getName()).isEqualTo(name);
    }

    @Test(dataProvider = "getCity", dependsOnMethods = "testAddCity")
    public void testUpdateCity(Long cityId, String name) {
        City city = cityRepository.findByName(name);
        String JSON = "json";
        String XML = "xml";
        Mono<CityWeatherDTO> cityWeatherDto = Mono.just(new CityWeatherDTO(cityId, JSON, XML));
        cityService.updateCity(city, cityWeatherDto);

        Optional<City> cityById = cityRepository.findByCityId(cityId);
        City upCity = cityById.get();
        assertEquals(upCity.getName(), name);
        Weather weather = upCity.getWeather();
        assertEquals(weather.getJson(), JSON);
        assertEquals(weather.getXml(), XML);
    }

}