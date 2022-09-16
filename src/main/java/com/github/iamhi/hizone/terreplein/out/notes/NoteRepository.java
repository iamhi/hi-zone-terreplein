package com.github.iamhi.hizone.terreplein.out.notes;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface NoteRepository extends ReactiveCrudRepository<NoteEntity, String> {

    Mono<NoteEntity> findByUuid(String uuid);

    Flux<NoteEntity> findByCreatedByUuid(String createdByUuid);

    Mono<NoteEntity> findByUuidAndCreatedByUuid(String uuid, String createdByUuid);
}
