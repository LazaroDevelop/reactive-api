package net.developer.space.reactive_api.services;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.dto.AuthRequest;
import net.developer.space.reactive_api.dto.AuthResponse;
import net.developer.space.reactive_api.entity.User;
import net.developer.space.reactive_api.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationHandler implements IAuthenticationHandler {

    private final JwtUtil util;
    private final IUserService userService;

    @Override
    public Mono<ServerResponse> login(ServerRequest request) {
        return request
                .bodyToMono(AuthRequest.class)
                .flatMap(auth -> userService.findByUsername(auth.getUsername())
                        .map(user -> {
                            if (user.getPassword().equals(auth.getPassword())) {
                                return ResponseEntity.ok(new AuthResponse(util.generateToken(auth.getUsername())));
                            } else {
                                throw new BadCredentialsException("Invalid username or password");
                            }
                        }).flatMap(res -> ServerResponse.ok().bodyValue(res))
                        .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password"))));
    }

    @Override
    public Mono<ServerResponse> signup(ServerRequest request) {
        return request
                .bodyToMono(User.class)
                .flatMap(user -> ServerResponse.ok().body(userService.storeUser(user), User.class))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    @Override
    public Mono<ServerResponse> protectedEndpoint(ServerRequest request) {
        return ServerResponse.ok().bodyValue("This is protected endpoint");
    }
}
