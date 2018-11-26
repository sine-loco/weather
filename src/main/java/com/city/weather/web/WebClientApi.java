package com.city.weather.web;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient connections settings
 */
@Component
public class WebClientApi {

    private final SiteProperties properties;

    @Autowired
    public WebClientApi(SiteProperties properties) {
        this.properties = properties;
    }

    public WebClient createWebClient() {
        return this.createWebClient(properties.getTimeout());
    }

    private WebClient createWebClient(int timeout) {

        HttpClient httpClient = getHttpClient(timeout);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    private HttpClient getHttpClient(int timeout) {
        return HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout));
    }

}
