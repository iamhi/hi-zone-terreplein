package com.github.iamhi.hizone.terreplein.in.notes;

import com.github.iamhi.hizone.terreplein.domain.notes.NoteDto;
import com.github.iamhi.hizone.terreplein.in.RequestExceptionHandler;
import com.github.iamhi.hizone.terreplein.in.ResponseSupplier;
import com.github.iamhi.hizone.terreplein.in.RouteRequest;
import com.github.iamhi.hizone.terreplein.in.shared.suppliers.UserTokenConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class NoteCreateRoute implements RouteRequest {

    private final ResponseSupplier<NoteDto> okResponseSupplier;

    private final RequestExceptionHandler requestExceptionHandler;

    private final CreateNoteAction createNoteAction;

    private final UserTokenConsumer userTokenConsumer;

    @Override
    @Bean("NoteCreateRoute")
    public RouterFunction<ServerResponse> get() {
        return route(POST("/note"), request -> request.bodyToMono(NoteCreateRequest.class)
            .flatMap(noteCreateRequest -> createNoteAction.apply(NoteDto.withTitleAndContent(
                noteCreateRequest.title,
                noteCreateRequest.content
            )))
            .contextWrite(context -> userTokenConsumer.apply(context, request))
            .flatMap(okResponseSupplier)
            .onErrorResume(requestExceptionHandler));
    }

    record NoteCreateRequest(
        String title,
        String content,
        String token
    ) {
    }
}
