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
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class TruthRoutes {

    private final ResponseSupplier<TruthDto> okResponseSupplier;

    private final RequestExceptionHandler requestExceptionHandler;

    private final UserTokenConsumer userTokenConsumer;

    private final CreateTruthAction createTruthAction;

    private final UpdateTruthAction updateTruthAction;

    private final DeleteTruthAction deleteTruthAction;

    @Bean("CreateTruthRoute")
    public RouterFunction<ServerResponse> createTruthRoute() {
        return route(POST("/truth"), request -> request.bodyToMono(CreateTruthRequest.class)
            .map(createTruthRequest -> TruthDto.withContent(
                createTruthRequest.content
            ))
            .flatMap(createTruthAction)
            .contextWrite(context -> userTokenConsumer.apply(context, request))
            .flatMap(okResponseSupplier)
            .onErrorResume(requestExceptionHandler));
    }

    @Bean("UpdateTruthRoute")
    public RouterFunction<ServerResponse> updateTruthRoute() {
        return route(PUT("/truth"), request -> request.bodyToMono(UpdateTruthRequest.class)
            .map(updateTruthRequest -> new TruthDto(
                updateTruthRequest.uuid,
                updateTruthRequest.content,
                ""
            ))
            .flatMap(updateTruthAction)
            .contextWrite(context -> userTokenConsumer.apply(context, request))
            .flatMap(okResponseSupplier)
            .onErrorResume(requestExceptionHandler));
    }

    @Bean("DeleteTruthRoute")
    public RouterFunction<ServerResponse> deleteTruthRoute() {
        return route(DELETE("/truth/{truthUuid}"), request -> Mono.just(request.pathVariable("truthUuid"))
            .flatMap(deleteTruthAction)
            .contextWrite(context -> userTokenConsumer.apply(context, request))
            .flatMap(okResponseSupplier)
            .onErrorResume(requestExceptionHandler));
    }

    record CreateTruthRequest(
        String content
    ) {
    }

    record UpdateTruthRequest(
        String uuid,
        String content
    ) {
    }
}
