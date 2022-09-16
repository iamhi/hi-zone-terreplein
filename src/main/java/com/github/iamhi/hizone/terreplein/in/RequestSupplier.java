package com.github.iamhi.hizone.terreplein.in;

import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface RequestSupplier<T> extends Function<ServerRequest, Mono<T>> {
}
