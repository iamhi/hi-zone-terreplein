package com.github.iamhi.hizone.terreplein.in;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Supplier;

public interface RouteRequest extends Supplier<RouterFunction<ServerResponse>> {
}
