package com.github.iamhi.hizone.terreplein.domain.services;

import com.github.iamhi.hizone.terreplein.out.auth.AuthenticationClient;
import com.github.iamhi.hizone.terreplein.out.auth.responses.UserInfoResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;

import static com.github.iamhi.hizone.terreplein.in.shared.suppliers.UserTokenConsumer.USER_TOKEN_CONTEXT;

@Service
public record UserService(
    AuthenticationClient authenticationClient
) {

    public Mono<UserInfoResponse> getUser() {
        return Mono.deferContextual(ctxView -> Mono.justOrEmpty(ctxView.getOrDefault(USER_TOKEN_CONTEXT, "")))
            .flatMap(userToken -> StringUtils.isBlank(userToken) ? Mono.error(AuthenticationException::new) : Mono.just(userToken))
            .flatMap(authenticationClient::fetchUserInfo);
    }
}
