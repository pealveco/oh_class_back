package com.pragma.tecnologia.domain.spi;

import reactor.core.publisher.Mono;
import com.pragma.tecnologia.domain.model.User;

public interface UserPersistencePort {
    Mono<User> save(User user);
    Mono<Boolean> existByEmail(String email);
}
