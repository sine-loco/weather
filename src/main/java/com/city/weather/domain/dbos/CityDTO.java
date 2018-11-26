package com.city.weather.domain.dbos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * JSON from POST
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    @NotEmpty
    private String name;
}
