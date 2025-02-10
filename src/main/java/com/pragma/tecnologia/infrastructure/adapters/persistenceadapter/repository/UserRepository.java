package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.UserEntity;


@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findByEmail(String email);
}
