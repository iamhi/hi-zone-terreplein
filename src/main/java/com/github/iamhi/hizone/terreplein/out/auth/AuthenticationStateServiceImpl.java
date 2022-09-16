package com.github.iamhi.hizone.terreplein.out.auth;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class AuthenticationStateServiceImpl implements AuthenticationStateService {

    String token = "unset";

    @Override
    public Mono<String> getToken() {
        return Mono.just(token);
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
