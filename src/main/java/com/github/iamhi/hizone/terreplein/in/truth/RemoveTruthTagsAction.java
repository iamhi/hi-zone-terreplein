package com.github.iamhi.hizone.terreplein.in.truth;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

@FunctionalInterface
public interface RemoveTruthTagsAction extends BiFunction<List<String>, String, Mono<TruthDto>> {
}
