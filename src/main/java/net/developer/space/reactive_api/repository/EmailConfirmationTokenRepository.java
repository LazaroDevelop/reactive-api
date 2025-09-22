package net.developer.space.reactive_api.repository;

import net.developer.space.reactive_api.entity.EmailConfirmationToken;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmailConfirmationTokenRepository extends ReactiveMongoRepository<EmailConfirmationToken, String> {
    EmailConfirmationToken findByToken(String token);
}
