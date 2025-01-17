package net.developer.space.reactive_api.router;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.services.IAuthenticationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class AuthRouter {

    private final IAuthenticationHandler handler;

    @Bean
    public RouterFunction<ServerResponse> authRouterFunction(){
        return RouterFunctions
                .route(RequestPredicates.POST("api/v1/auth/login"), handler::login)
                .andRoute(RequestPredicates.POST("api/v1/auth/signup"), handler::signup)
                .andRoute(RequestPredicates.GET("api/v1/protected"), handler::protectedEndpoint)
                .andRoute(RequestPredicates.POST("api/v1/otp/verify"), handler::verifyOtp);
    }
}
