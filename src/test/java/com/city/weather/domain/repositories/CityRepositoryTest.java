package com.city.weather.domain.repositories;

import com.city.weather.domain.entities.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

// sine-loco. why does one need ugly junit:4?
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CityRepositoryTest {

    private static final long CITY_ID = 10L;
    private static final String CITY_NAME = "F-" + CITY_ID;
    private int allCities = 0;
    private int sameNameCities = 0;

    @Autowired
    private CityRepository repository;

    @Before
    public void setUp() {
        repository.deleteAllInBatch();
        IntStream.rangeClosed(1, 2).forEach(i -> {
            addCity(i, CITY_NAME);
            sameNameCities++;
        });
        IntStream.rangeClosed(3, 5).forEach(i -> addCity(i, CITY_NAME.replace("" + CITY_ID, "") + i * CITY_ID));
    }

    private void addCity(int i, String name) {
        City city = new City();
        final long cityId = i * CITY_ID;
        city.setCityId(cityId);
        city.setName(name);
        repository.save(city);
        allCities++;
    }

    @Test
    public void testGetCities() {
        List<City> cities = repository.findAll();
        assertThat(cities.size(), is(allCities));
    }

    @Test
    public void testGetCity() {
        Optional<City> cityOptional = repository.findByCityId(CITY_ID);
        if (cityOptional.isPresent()) {
            assertThat(cityOptional.get().getName(), is(CITY_NAME));
        } else {
            assert false;
        }
    }

}