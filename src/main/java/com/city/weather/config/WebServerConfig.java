package com.city.weather.config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import reactor.netty.http.server.HttpServer;

@Component
public class WebServerConfig
        implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        factory.addServerCustomizers(new EventLoopNettyCustomizer());
    }
}

class EventLoopNettyCustomizer implements NettyServerCustomizer {

    private static final int THREADS_NUMBER = 3;

    @Override
    public HttpServer apply(HttpServer httpServer) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(THREADS_NUMBER);
        return httpServer.tcpConfiguration(tcpServer ->
                tcpServer.bootstrap(serverBootstrap
                        -> serverBootstrap.group(eventLoopGroup)));
    }
}