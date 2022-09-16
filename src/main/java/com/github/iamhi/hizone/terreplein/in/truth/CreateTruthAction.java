package com.github.iamhi.hizone.terreplein.in.truth;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@FunctionalInterface
public interface CreateTruthAction extends Function<TruthDto, Mono<TruthDto>> {
}
