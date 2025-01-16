package net.developer.space.reactive_api.services;

import lombok.RequiredArgsConstructor;
import net.developer.space.reactive_api.entity.User;
import net.developer.space.reactive_api.repository.UserReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserReactiveRepository repository;

    @Override
    public Mono<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Mono<User> storeUser(User user) {
        //TODO Encrypt password before save
        return repository.save(user);
    }
}
