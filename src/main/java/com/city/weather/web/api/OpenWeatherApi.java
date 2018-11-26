package com.city.weather.web.api;

import com.city.weather.web.config.SiteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * API for weather site
 */
@Component
public class OpenWeatherApi {

    private final SiteProperties properties;
    private final WebClientApi webClientApi;

    @Autowired
    public OpenWeatherApi(SiteProperties properties, WebClientApi webClientApi) {
        this.properties = properties;
        this.webClientApi = webClientApi;
    }

    public Mono<ServerResponse> inCity(String city, String media) {

        return getUri(city, media)
                .exchange()
                .flatMap((ClientResponse mapper) -> ServerResponse.status(mapper.statusCode())
                        .headers(c -> mapper.headers().asHttpHeaders().forEach(c::put))
                        .body(mapper.bodyToMono(String.class), String.class));
    }

    private WebClient.RequestHeadersSpec<?> getUri(String city, String media) {
        return webClientApi.createWebClient()
                .get()
                .uri(builder -> builder.scheme("http")
                        .host(properties.getHost()).path(properties.getPath())
                        .queryParam("q", city)
                        .queryParam("appid", properties.getAppid())
                        .queryParam("mode", media)
                        .build());
    }

}
