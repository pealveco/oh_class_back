package com.pragma.tecnologia.domain.api;

import reactor.core.publisher.Mono;
import com.pragma.tecnologia.domain.model.User;

public interface UserServicePort {
    Mono<User> registerUser(User user, String messageId);
}
