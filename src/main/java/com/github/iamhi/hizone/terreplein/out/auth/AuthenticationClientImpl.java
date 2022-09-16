package com.github.iamhi.hizone.terreplein.out.auth;

import com.github.iamhi.hizone.terreplein.config.AuthConfig;
import com.github.iamhi.hizone.terreplein.out.auth.requests.LoginRequest;
import com.github.iamhi.hizone.terreplein.out.auth.requests.TokenValidRequest;
import com.github.iamhi.hizone.terreplein.out.auth.requests.UserInfoRequest;
import com.github.iamhi.hizone.terreplein.out.auth.responses.LoginResponse;
import com.github.iamhi.hizone.terreplein.out.auth.responses.TokenValidResponse;
import com.github.iamhi.hizone.terreplein.out.auth.responses.UserInfoResponse;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
record AuthenticationClientImpl(
    AuthConfig authConfig,
    AuthenticationStateService authenticationStateService,
    WebClient.Builder builder
) implements AuthenticationClient {

    private static final String PING_ROUTE = "/status/ping";
    private static final String ROUTER_PREFIX = "/service";
    private static final String SERVICE_LOGIN_ROUTE = ROUTER_PREFIX + "/login";
    private static final String SERVICE_USER_INFO_ROUTE = ROUTER_PREFIX + "/user-info";
    private static final String SERVICE_TOKEN_VALID_ROUTE = ROUTER_PREFIX + "/token-valid";

    @PostConstruct
    private void initialConnect() {
        this.connect().block();
    }

    @Override
    public Mono<Boolean> connect() {
        return getClient()
            .post()
            .uri(SERVICE_LOGIN_ROUTE)
            .bodyValue(new LoginRequest(authConfig.getUsername(), authConfig.getPassword()))
            .retrieve()
            .bodyToMono(LoginResponse.class)
            .map(loginResponse -> {
                authenticationStateService.setToken(loginResponse.token());
                return true;
            });
    }

    @Override
    public Mono<Boolean> isConnected() {
        return getClient()
            .post()
            .uri(SERVICE_TOKEN_VALID_ROUTE)
            .body(authenticationStateService
                .getToken()
                .map(TokenValidRequest::new), TokenValidRequest.class)
            .retrieve()
            .bodyToMono(TokenValidResponse.class)
            .map(TokenValidResponse::uuid)
            .map(StringUtils::isNotBlank);
    }

    @Override
    public Mono<Boolean> hasAccessibility() {
        return getClient()
            .get()
            .uri(PING_ROUTE)
            .retrieve()
            .bodyToMono(Map.class)
            .map(responseMap -> responseMap.size() > 0);
    }

    @Override
    public Mono<UserInfoResponse> fetchUserInfo(String userToken) {
        return getClient()
            .post()
            .uri(SERVICE_USER_INFO_ROUTE)
            .body(
                authenticationStateService.getToken()
                    .map(token -> new UserInfoRequest(token, userToken)),
                UserInfoRequest.class)
            .retrieve()
            .bodyToMono(UserInfoResponse.class);
    }

    private WebClient getClient() {
        return WebClient.builder()
            .baseUrl(authConfig.getBaseUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
