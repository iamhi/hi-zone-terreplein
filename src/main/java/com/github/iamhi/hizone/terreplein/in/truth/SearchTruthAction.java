package com.github.iamhi.hizone.terreplein.in.truth;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface SearchTruthAction extends Function<List<String>, Flux<TruthDto>> {
}
