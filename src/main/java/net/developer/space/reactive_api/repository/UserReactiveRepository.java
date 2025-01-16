package net.developer.space.reactive_api.repository;


import net.developer.space.reactive_api.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface UserReactiveRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
