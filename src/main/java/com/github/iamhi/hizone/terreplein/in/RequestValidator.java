package com.github.iamhi.hizone.terreplein.in;

import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface RequestValidator<T> extends Function<T, Mono<T>> {
}

