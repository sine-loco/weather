package com.city.weather.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WeatherUpdateService {

    private final CityService cityService;

    @Autowired
    public WeatherUpdateService(CityService cityService) {
        this.cityService = cityService;
    }

    // TODO catch Unexpected error
    @Scheduled(fixedDelayString = "${update.delay}")
    public void scheduler() {
        cityService.updateCities();
    }

}
