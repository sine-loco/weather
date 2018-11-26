package com.city.weather.utl.exeptions;

public class CityExistException extends RuntimeException {
    public CityExistException() {
        super("City already exist!");
    }
}
