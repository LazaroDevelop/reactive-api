package net.developer.space.reactive_api.services;

import net.developer.space.reactive_api.entity.User;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> findByUsername(String username);
    Mono<User> storeUser(User user);
}
