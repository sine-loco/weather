package com.city.weather.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Weather's site settings
 */
@Data
@ConfigurationProperties(prefix = "site")
@Component
public class SiteProperties {
    private String appid;
    private String host;
    private String path;
    private int timeout;
}
