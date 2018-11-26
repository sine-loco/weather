package com.city.weather.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.Serializable;

/**
 * weather responses from the site
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Weather implements Serializable {
    private static final long serialVersionUID = 1L;

    @Lob
    @Column(name = "JSON")
    private String json;

    @Lob
    @Column(name = "XML")
    private String xml;
}