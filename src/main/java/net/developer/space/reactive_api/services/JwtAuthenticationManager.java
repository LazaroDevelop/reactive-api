package net.developer.space.reactive_api.services;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.utils.JwtUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil util;
    private final IUserService userService;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String token = authentication.getCredentials().toString();
        String username = util.extractUsername(token);

        return userService
                .findByUsername(username)
                .map(userDetails ->  {
                    if(util.validateToken(token, userDetails.getUsername())){
                        return authentication;
                    }else{
                        throw new AuthenticationException("Invalid JWT token") {};
                    }
                });
    }


    public ServerAuthenticationConverter authenticationConverter(){
        return exchange -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(token != null && token.startsWith("Bearer ")){
                token = token.substring(7);

                return Mono.just(SecurityContextHolder.getContext().getAuthentication());
            }

            return Mono.empty();
        };
    }
}
