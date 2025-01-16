package net.developer.space.reactive_api.services;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IAuthenticationHandler {
    Mono<ServerResponse> login(ServerRequest request);
    Mono<ServerResponse> signup(ServerRequest request);
    Mono<ServerResponse> protectedEndpoint(ServerRequest request);
 }
