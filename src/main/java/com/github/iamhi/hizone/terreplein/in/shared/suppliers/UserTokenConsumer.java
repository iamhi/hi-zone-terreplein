package com.github.iamhi.hizone.terreplein.in.shared.suppliers;

import com.github.iamhi.hizone.terreplein.config.AuthConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.util.context.Context;

import java.util.function.BiFunction;

@Component
public record UserTokenConsumer(
    AuthConfig authConfig
) implements BiFunction<Context, ServerRequest, Context> {

    public static String USER_TOKEN_CONTEXT = "user-token";

    @Override
    public Context apply(Context context, ServerRequest serverRequest) {
        HttpCookie httpCookie = serverRequest.cookies().getFirst(authConfig.getAccessTokenName());

        if (httpCookie != null && StringUtils.isNotBlank(httpCookie.getValue())) {
            return context.put(USER_TOKEN_CONTEXT, httpCookie.getValue());
        }

        String authorizationHeader = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(authorizationHeader)) {
            return context.put(USER_TOKEN_CONTEXT, authorizationHeader);
        }

        serverRequest.queryParam("clientToken")
            .ifPresent(clientToken -> context.put(USER_TOKEN_CONTEXT, clientToken));

        return context;
    }
}
