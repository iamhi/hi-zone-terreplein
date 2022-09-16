package com.github.iamhi.hizone.terreplein.in.truth;

import com.github.iamhi.hizone.terreplein.domain.models.TruthDto;
import com.github.iamhi.hizone.terreplein.in.RequestExceptionHandler;
import com.github.iamhi.hizone.terreplein.in.ResponseSupplier;
import com.github.iamhi.hizone.terreplein.in.shared.suppliers.UserTokenConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class TagRoutes {

    private final ResponseSupplier<TruthDto> okResponseSupplier;

    private final RequestExceptionHandler requestExceptionHandler;

    private final UserTokenConsumer userTokenConsumer;

    private final AddTruthTagsAction addTruthTagsAction;

    private final RemoveTruthTagsAction removeTruthTagsAction;

    @Bean("addTruthTagsRoute")
    RouterFunction<ServerResponse> addTruthTagsRoute() {
        return route(POST("/tags/{truthUuid}"), request -> request.bodyToMono(TagsRequest.class)
            .map(TagsRequest::tags)
            .flatMap(tags -> addTruthTagsAction.apply(tags, request.pathVariable("truthUuid")))
            .contextWrite(context -> userTokenConsumer.apply(context, request))
            .flatMap(okResponseSupplier)
            .onErrorResume(requestExceptionHandler));
    }


    @Bean("removeTruthTagsRoute")
    RouterFunction<ServerResponse> removeTruthTagsRoute() {
        return route(DELETE("/tags/{truthUuid}"), request -> request.bodyToMono(TagsRequest.class)
            .map(TagsRequest::tags)
            .flatMap(tags -> removeTruthTagsAction.apply(tags, request.pathVariable("truthUuid")))
            .contextWrite(context -> userTokenConsumer.apply(context, request))
            .flatMap(okResponseSupplier)
            .onErrorResume(requestExceptionHandler));
    }

    record TagsRequest(List<String> tags) {
    }
}
