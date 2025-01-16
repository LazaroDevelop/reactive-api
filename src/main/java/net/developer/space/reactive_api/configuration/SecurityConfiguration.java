package net.developer.space.reactive_api.configuration;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.services.JwtAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

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
                                .pathMatchers("/api/v1/auth/*").permitAll()
                                .anyExchange().authenticated())
                .authenticationManager(authenticationManager)
                .addFilterBefore(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public AuthenticationWebFilter authenticationWebFilter(){
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);

        filter.setServerAuthenticationConverter(authenticationManager.authenticationConverter());
        filter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        return filter;

    }
}
