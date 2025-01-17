package net.developer.space.reactive_api.configuration;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.services.JwtAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationManager authenticationManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        ex -> ex
                                .pathMatchers("/api/v1/auth/*", "api/v1/otp/verify").permitAll()
                                .anyExchange().authenticated()
                )
                .authenticationManager(authenticationManager)
                .build();
    }
}
