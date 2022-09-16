package com.github.iamhi.hizone.terreplein.domain.services;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import com.github.iamhi.hizone.terreplein.in.truth.SearchTruthAction;
import com.github.iamhi.hizone.terreplein.out.truth.TruthEntity;
import com.github.iamhi.hizone.terreplein.out.truth.TruthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class SearchService {

    private final UserService userService;

    private final TruthRepository truthRepository;

    private final Function<TruthEntity, TruthDto> fromEntity;

    @Bean
    SearchTruthAction searchTruthAction() {
        return tags -> truthRepository.findByTagsLike(generateLikeString(tags))
            .concatWith(truthRepository.findByTagsLike(generateExactString(tags)))
            .map(fromEntity);
    }

    private String generateLikeString(List<String> tags) {
        Collections.sort(tags);

        return "%;" + String.join(";%;", tags) + ";%";
    }

    private String generateExactString(List<String> tags) {
        Collections.sort(tags);

        return ";" + String.join(";", tags) + ";";
    }
}
