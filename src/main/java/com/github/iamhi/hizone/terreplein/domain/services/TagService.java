package com.github.iamhi.hizone.terreplein.domain.services;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import com.github.iamhi.hizone.terreplein.in.truth.AddTruthTagsAction;
import com.github.iamhi.hizone.terreplein.in.truth.RemoveTruthTagsAction;
import com.github.iamhi.hizone.terreplein.out.truth.TruthEntity;
import com.github.iamhi.hizone.terreplein.out.truth.TruthRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class TagService {

    private final TruthRepository truthRepository;

    private final UserService userService;

    private final Function<TruthEntity, TruthDto> fromEntity;

    @Bean
    AddTruthTagsAction addTruthTagsAction() {
        return (tags, truthUuid) ->
            userService.getUser()
                .flatMap(userInfoResponse -> truthRepository.findByUuidAndCreatedByUuid(truthUuid, userInfoResponse.uuid()))
                .map(truthEntity -> addTags(truthEntity, tags))
                .flatMap(truthRepository::save)
                .map(fromEntity);
    }

    @Bean
    RemoveTruthTagsAction removeTruthTagsAction() {
        return (tags, truthUuid) ->
            userService.getUser()
                .flatMap(userInfoResponse -> truthRepository.findByUuidAndCreatedByUuid(truthUuid, userInfoResponse.uuid()))
                .map(truthEntity -> removeTags(truthEntity, tags))
                .flatMap(truthRepository::save)
                .map(fromEntity);
    }

    private TruthEntity addTags(TruthEntity truthEntity, List<String> addTags) {
        List<String> currentTags = Arrays.asList(truthEntity.getTags().split(";"));

        truthEntity.setTags(";" +
            Stream.concat(currentTags.stream(), addTags.stream())
                .filter(StringUtils::isNotBlank)
                .sorted()
                .distinct().collect(Collectors.joining(";")) + ";");

        return truthEntity;
    }

    private TruthEntity removeTags(TruthEntity truthEntity, List<String> removeTags) {
        List<String> currentTags = Arrays.asList(truthEntity.getTags().split(";"));

        truthEntity.setTags(";" + currentTags.stream().filter(tag -> !removeTags.contains(tag)).collect(Collectors.joining(";")) + ";");

        return truthEntity;
    }
}
