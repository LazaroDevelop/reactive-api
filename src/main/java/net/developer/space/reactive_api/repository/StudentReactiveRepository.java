package net.developer.space.reactive_api.repository;

import net.developer.space.reactive_api.entity.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableReactiveMongoRepositories
public interface StudentReactiveRepository extends ReactiveMongoRepository<Student, String> {}
