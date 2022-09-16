package com.github.iamhi.hizone.terreplein.in.exceptions;

import com.github.iamhi.hizone.terreplein.in.RequestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record DefaultExceptionHandler() implements RequestExceptionHandler {

    @Override
    public Mono<ServerResponse> apply(Throwable throwable) {
        if (throwable instanceof AccessTokenNotFoundThrowable) {
            return ServerResponse.badRequest().bodyValue(throwable.getMessage());
        }

        if (throwable instanceof ValidationErrorThrowable validationError) {
            return ServerResponse.badRequest().bodyValue(validationError.getErrors());
        }

        throwable.printStackTrace();

        // return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // if production
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(throwable); // if local/dev
    }
}
