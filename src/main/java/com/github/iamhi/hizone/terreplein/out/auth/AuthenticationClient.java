package com.github.iamhi.hizone.terreplein.out.auth;

import com.github.iamhi.hizone.terreplein.out.auth.responses.UserInfoResponse;
import reactor.core.publisher.Mono;

public interface AuthenticationClient {

    Mono<Boolean> connect();

    Mono<Boolean> isConnected();

    Mono<Boolean> hasAccessibility();

    Mono<UserInfoResponse> fetchUserInfo(String token);
}
