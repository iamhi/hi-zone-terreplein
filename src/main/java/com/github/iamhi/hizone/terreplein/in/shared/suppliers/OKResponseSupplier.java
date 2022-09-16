package com.github.iamhi.hizone.terreplein.in.shared.suppliers;

import com.github.iamhi.hizone.terreplein.in.ResponseSupplier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component("okResponseSupplier")
public class OKResponseSupplier<T> implements ResponseSupplier<T> {

    @Override
    public Mono<ServerResponse> apply(T t) {
        return ServerResponse.ok().bodyValue(t);
    }
}
