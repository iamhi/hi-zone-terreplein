package com.github.iamhi.hizone.terreplein.out.auth;

import reactor.core.publisher.Mono;

interface AuthenticationStateService {

    Mono<String> getToken();

    void setToken(String token);
}
