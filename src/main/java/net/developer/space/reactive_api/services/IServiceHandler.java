package net.developer.space.reactive_api.services;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IServiceHandler {
    Mono<ServerResponse> getAllStudents(ServerRequest request);
    Mono<ServerResponse> addStudent(ServerRequest request);
    Mono<ServerResponse> findStudentById(ServerRequest request);
    Mono<ServerResponse> updateStudent(ServerRequest request);
    Mono<ServerResponse> deleteStudent(ServerRequest request);
}
