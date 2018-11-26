package com.city.weather.domain.dbos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Weather data from JSON
 * <p>
 * Converted from json to DTO on site http://pojo.sodhanalibrary.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    private String id;
    private String dt;
    private Clouds clouds;
    private Coord coord;
    private Wind wind;
    private String cod;
    private String visibility;
    private Sys sys;
    private String name;
    private String base;
    private Weather[] weather;
    private Main main;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Sys {
    private String message;
    private String id;
    private String sunset;
    private String sunrise;
    private String type;
    private String country;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Clouds {
    private String all;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Coord {
    private String lon;
    private String lat;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Wind {
    private String gust;
    private String speed;
    private String deg;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Weather {
    private String id;
    private String icon;
    private String description;
    private String main;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Main {
    private String humidity;
    private String pressure;
    private String temp_max;
    private String temp_min;
    private String temp;
}