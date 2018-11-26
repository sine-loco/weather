package com.city.weather.web;

import com.city.weather.domain.dbos.CityDTO;
import com.city.weather.domain.entities.City;
import com.city.weather.domain.entities.Weather;
import com.city.weather.services.CityService;
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

    private final CityService cityService;
    private final OpenWeatherApi openWeatherApi;

    @Autowired
    public RouterHelper(OpenWeatherApi openWeatherApi, CityService cityService) {
        this.openWeatherApi = openWeatherApi;
        this.cityService = cityService;
    }

    public Mono<ServerResponse> getCityWeather(ServerRequest request) {

        final Optional<String> cityOptional = request.queryParam("city");
        if (!cityOptional.isPresent()) return ServerResponse.badRequest().build();
        final String city = cityOptional.get();
        final MediaType mediaType = this.getMediaType(request);

        return cityService.findCity(Mono.just(new CityDTO(city)))
                .flatMap(w -> ServerResponse.ok()
                        .contentType(mediaType)
                        .body(Mono.just(getCachedWeather(w, mediaType)), String.class))
                .switchIfEmpty(openWeatherApi.inCity(city, mediaType.getSubtype()));
    }

    // will be cached later
    private String getCachedWeather(City city, MediaType mediaType) {
        final Weather weather = city.getWeather();
        return (mediaType == MediaType.APPLICATION_XML) ? weather.getXml() : weather.getJson();
    }

    private MediaType getMediaType(ServerRequest serverRequest) {
        final Optional<String> modeOptional = serverRequest.queryParam("mode");
        final Optional<MediaType> mediaType = serverRequest.headers().contentType();
        if (mediaType.isPresent()) {
            return mediaType.get().equals(MediaType.APPLICATION_XML)
                    ? MediaType.APPLICATION_XML : MediaType.APPLICATION_JSON;
        }
        if (modeOptional.isPresent() && ("xml".equals(modeOptional.get()))) {
            return MediaType.APPLICATION_XML;
        }
        return MediaType.APPLICATION_JSON;
    }


}

