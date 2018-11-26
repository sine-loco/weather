package com.city.weather.domain.dbos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityWeatherDTO {

    private Long cityId;
    private String json;
    private String xml;
}
