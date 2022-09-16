package com.github.iamhi.hizone.terreplein.out.truth;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TruthRepository extends ReactiveCrudRepository<TruthEntity, Integer> {

    Mono<TruthEntity> findByUuid(String uuid);

    Mono<TruthEntity> findByUuidAndCreatedByUuid(String uuid, String createdByUuid);

    Flux<TruthEntity> findByTagsLike(String tags);
}
