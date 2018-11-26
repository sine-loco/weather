package com.city.weather.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherUpdateService {

    private final CityService cityService;

    // TODO catch Unexpected error
    @Scheduled(fixedDelayString = "${update.delay}")
    public void scheduler() {
        cityService.updateCities();
    }

}
