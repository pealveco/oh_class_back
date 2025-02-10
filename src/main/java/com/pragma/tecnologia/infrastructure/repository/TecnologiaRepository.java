package com.pragma.tecnologia.infrastructure.repository;

import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.TecnologiaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TecnologiaRepository extends ReactiveCrudRepository<TecnologiaEntity, Long> {
    Mono<Boolean> existsByName(String name);
}