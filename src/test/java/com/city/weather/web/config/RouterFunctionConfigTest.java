package com.city.weather.web.config;

import com.city.weather.domain.dbos.CityDTO;
import com.city.weather.domain.entities.City;
import com.city.weather.domain.entities.Weather;
import com.city.weather.services.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RouterFunctionConfigTest {

    @Autowired
    RouterFunction<ServerResponse> cityWeather;

    @MockBean
    CityService cityService;

    private static final String CITY_NAME = "NYC";

    @Test
    public void cityWeather() {

        final City city = testCity(CITY_NAME);
        final Mono<City> cityMono = Mono.just(city);
        final Mono<CityDTO> cityDtoMono = Mono.just(new CityDTO(CITY_NAME));

        when(cityService.findCity(any())).thenReturn(cityMono);

        WebTestClient testClient = WebTestClient.bindToRouterFunction(cityWeather).build();

        final Function<UriBuilder, URI> uri = builder -> builder
                .path("/weather")
                .queryParam("city", CITY_NAME)
                .build();
        testClient.get().uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(city.getWeather().getJson());
    }

    private City testCity(String name) {
        City city = new City(name);
        city.setId(1L);
        city.setCityId(2L);
        city.setWeather(new Weather("json", "xml"));
        return city;
    }

}