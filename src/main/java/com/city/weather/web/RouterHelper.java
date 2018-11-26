package com.city.weather.web;

import com.city.weather.web.api.OpenWeatherApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "WeatherCache")
public class RouterHelper {

    private final OpenWeatherApi openWeatherApi;

    @Autowired
    public RouterHelper(OpenWeatherApi openWeatherApi) {
        this.openWeatherApi = openWeatherApi;
    }

    public Mono<ServerResponse> getCityWeather(ServerRequest request) {

        final Optional<String> cityOptional = request.queryParam("city");
        if (!cityOptional.isPresent()) return ServerResponse.badRequest().build();
        final String city = cityOptional.get();
        final MediaType mediaType = MediaType.APPLICATION_JSON;

        return openWeatherApi.inCity(city, mediaType.getSubtype());
    }

}

