package com.pragma.bootcamp.domain.spi;

import com.pragma.bootcamp.domain.model.Bootcamp;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcampPersistencePort {
    Mono<Bootcamp> save(Bootcamp bootcamp);
    Mono<Boolean> existsByName(String name);
    Flux<Bootcamp> findAll(Pageable pageable);
}