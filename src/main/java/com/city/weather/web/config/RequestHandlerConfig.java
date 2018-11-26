package com.city.weather.web.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Validator;
import java.util.function.Function;

@Component
public class RequestHandlerConfig {

    private final Validator validator;

    public RequestHandlerConfig(Validator validator) {
        this.validator = validator;
    }

    public <B> Mono<ServerResponse> requireValidBody(
            Function<Mono<B>, Mono<ServerResponse>> block,
            ServerRequest request, Class<B> bodyClass) {

        return request
                .bodyToMono(bodyClass)
                .flatMap(
                        b -> validator.validate(b).isEmpty()
                                ? block.apply(Mono.just(b))
                                : ServerResponse.unprocessableEntity().build()
                );
    }
}