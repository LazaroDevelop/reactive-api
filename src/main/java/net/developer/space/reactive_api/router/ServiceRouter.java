package net.developer.space.reactive_api.router;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.services.IServiceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class ServiceRouter {

    private final IServiceHandler handler;

    @Bean
    RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions
                .route(RequestPredicates.POST("api/v1/student/add"), handler::addStudent)
                .andRoute(RequestPredicates.PUT("api/v1/student/update"), handler::updateStudent)
                .andRoute(RequestPredicates.POST("api/v1/student/all"), handler::getAllStudents)
                .andRoute(RequestPredicates.DELETE("api/v1/student/delete"), handler::deleteStudent);
    }

}
