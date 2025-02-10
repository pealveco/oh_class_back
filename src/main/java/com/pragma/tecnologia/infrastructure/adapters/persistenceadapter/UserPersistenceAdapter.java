package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import com.pragma.tecnologia.domain.model.User;
import com.pragma.tecnologia.domain.spi.UserPersistencePort;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.UserEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.UserRepository;

@AllArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(userEntityMapper.toEntity(user))
                .map(userEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toModel)
                .map(user -> true)  // Si encuentra el usuario, devuelve true
                .defaultIfEmpty(false);  // Si no encuentra, devuelve false
    }

}
