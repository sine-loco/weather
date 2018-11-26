package com.city.weather.web.config;

import com.city.weather.web.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final RouterHelper routerHelper;

    @Autowired
    public RouterFunctionConfig(RouterHelper routerHelper) {
        this.routerHelper = routerHelper;
    }

    @Bean
    public RouterFunction<ServerResponse> cityWeather() {
        return route(GET("/weather"), routerHelper::getCityWeather)
                .andRoute(GET("/cities"), request -> routerHelper.allCities())
                .andRoute(POST("/cities/add"), routerHelper::addCity);
    }
}
