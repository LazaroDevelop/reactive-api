package net.developer.space.reactive_api.services;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.entity.Student;
import net.developer.space.reactive_api.repository.StudentReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ServiceHandler implements IServiceHandler{

    private final StudentReactiveRepository repository;

    @Override
    public Mono<ServerResponse> getAllStudents(ServerRequest request) {
        return ServerResponse.ok().body(repository.findAll(), Student.class)
                .switchIfEmpty(ServerResponse.ok().bodyValue("No data found"));
    }

    @Override
    public Mono<ServerResponse> addStudent(ServerRequest request) {
        return request
                .bodyToMono(Student.class)
                .flatMap(data -> ServerResponse.ok().body(repository.save(data), Student.class));
    }

    @Override
    public Mono<ServerResponse> findStudentById(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(item -> ServerResponse.ok().bodyValue(item))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    @Override
    public Mono<ServerResponse> updateStudent(ServerRequest request) {
        String id = request.pathVariable("id");
        return request
                .bodyToMono(Student.class)
                .flatMap(data -> repository.findById(id).flatMap(dataToUpdate -> {
                    dataToUpdate.setId(data.getId());
                    dataToUpdate.setStudentName(data.getStudentName());
                    dataToUpdate.setStudentAge(data.getStudentAge());
                    return ServerResponse.ok().body(repository.save(dataToUpdate), Student.class);
                }).switchIfEmpty(ServerResponse.notFound().build()));
    }

    @Override
    public Mono<ServerResponse> deleteStudent(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.deleteById(id)
                .then(Mono.defer(() -> ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
