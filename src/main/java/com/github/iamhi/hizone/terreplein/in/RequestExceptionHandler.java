package com.github.iamhi.hizone.terreplein.in;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface RequestExceptionHandler extends Function<Throwable, Mono<ServerResponse>> {
}
