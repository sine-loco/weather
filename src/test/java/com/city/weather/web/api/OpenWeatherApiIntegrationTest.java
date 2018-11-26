package com.city.weather.web.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherApiIntegrationTest {

    @Autowired
    OpenWeatherApi openWeatherApi;

    @Test
    public void inCity() {

        final Mono<ServerResponse> serverResponseMono = openWeatherApi.inCity("Moscow", "json");

        StepVerifier
                .create(serverResponseMono)
                .expectNextMatches(s -> s.statusCode().equals(HttpStatus.OK) && s.headers().size() == 9)
                .verifyComplete();
    }

}