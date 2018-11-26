package com.city.weather.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * City info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CITIES")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class City implements Serializable {
    private static final long serialVersionUID = 1L;

    public City(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CITY_ID", unique = true)
    private Long cityId;

    @Column(name = "NAME")
    private String name;

    @Embedded
    private Weather weather;

    @UpdateTimestamp
    @Column(name = "TIME")
    private LocalDateTime updateDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(getCityId(), city.getCityId()) &&
                Objects.equals(getName(), city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCityId(), getName());
    }
}