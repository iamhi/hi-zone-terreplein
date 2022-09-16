package com.github.iamhi.hizone.terreplein.in.truth;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import com.github.iamhi.hizone.terreplein.in.RequestExceptionHandler;
import com.github.iamhi.hizone.terreplein.in.ResponseSupplier;
import com.github.iamhi.hizone.terreplein.in.shared.suppliers.UserTokenConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class SearchRoute {

    private final RequestExceptionHandler requestExceptionHandler;

    private final UserTokenConsumer userTokenConsumer;

    private final SearchTruthAction searchTruthAction;

    @Bean("searchTruth")
    public RouterFunction<ServerResponse> searchTruth() {
        return route(POST("/search"), request -> ServerResponse.ok().body(request.bodyToMono(SearchRequest.class)
                .map(SearchRequest::tags)
                .flatMapMany(searchTruthAction)
                .contextWrite(context -> userTokenConsumer.apply(context, request)), TruthDto.class)
            .onErrorResume(requestExceptionHandler));
    }

    record SearchRequest(List<String> tags) {
    }
}
