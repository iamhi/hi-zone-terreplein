package com.github.iamhi.hizone.terreplein.domain.services;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import com.github.iamhi.hizone.terreplein.in.truth.AddTruthTagsAction;
import com.github.iamhi.hizone.terreplein.in.truth.CreateTruthAction;
import com.github.iamhi.hizone.terreplein.in.truth.DeleteTruthAction;
import com.github.iamhi.hizone.terreplein.in.truth.UpdateTruthAction;
import com.github.iamhi.hizone.terreplein.out.auth.responses.UserInfoResponse;
import com.github.iamhi.hizone.terreplein.out.truth.TruthEntity;
import com.github.iamhi.hizone.terreplein.out.truth.TruthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class TruthService {

    final TruthRepository truthRepository;

    final UserService userService;

    @Bean
    CreateTruthAction createTruthAction() {
        return truthDto -> userService.getUser().map(userInfoResponse -> createEntity(userInfoResponse, truthDto))
            .flatMap(truthRepository::save)
            .map(fromEntity());
    }

    @Bean
    DeleteTruthAction deleteTruthAction() {
        return truthUuid -> userService.getUser()
            .flatMap(userInfoResponse -> truthRepository.findByUuidAndCreatedByUuid(truthUuid, userInfoResponse.uuid())
                .flatMap(truth -> truthRepository.delete(truth)
                    .then(Mono.just(truth).map(fromEntity()))));
    }

    @Bean
    UpdateTruthAction updateTruthAction() {
        return truthDto -> userService.getUser()
            .flatMap(newEntityUserInfoResponse -> truthRepository
                .findByUuidAndCreatedByUuid(truthDto.uuid(), newEntityUserInfoResponse.uuid())
                .map(oldEntity -> updateEntity(oldEntity, truthDto.content()))
                .flatMap(truthRepository::save)
                .map(fromEntity()));
    }

    TruthEntity createEntity(UserInfoResponse userInfoResponse, TruthDto truthDto) {
        return new TruthEntity(
            null,
            UUID.randomUUID().toString(),
            truthDto.content(),
            truthDto.tags(),
            userInfoResponse.uuid(),
            Instant.now(),
            Instant.now()
        );
    }

    private TruthEntity updateEntity(TruthEntity oldEntity, String content) {
        oldEntity.setContent(content);
        oldEntity.setUpdatedAt(Instant.now());

        return oldEntity;
    }

    @Bean
    Function<TruthEntity, TruthDto> fromEntity() {
        return truth -> new TruthDto(
            truth.getUuid(),
            truth.getContent(),
            truth.getTags()
        );
    }
}
