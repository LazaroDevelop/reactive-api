package net.developer.space.reactive_api.services;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.utils.JwtToken;
import net.developer.space.reactive_api.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtUtil jwtUtil;
    private static final String BEARER = "Bearer ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header ->  header.startsWith(BEARER))
                .map(header -> header.substring(BEARER.length()))
                .map(token -> new JwtToken(token, createUserDetails(token)));
    }

    private UserDetails createUserDetails(String token){
        String username = jwtUtil.extractUsername(token);
        return User.builder()
                .username(username)
                .password("")
                .build();
    }

//    private List<SimpleGrantedAuthority> createAuthorities(String token){
//        return jwtUtil.extractRoles(token).stream()
//                .map(role -> "ROLE_" +  role)
//                .map(SimpleGrantedAuthority::new)
//                .toList();
//    }

}
