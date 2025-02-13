package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Capacidad;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CapacidadPersistencePort {
    Mono<Capacidad> save(Capacidad capacidad);
    Flux<Capacidad> findAll();
    Flux<Capacidad> findAll(Pageable pageable);
    Mono<Capacidad> findById(Long id);
    Mono<Void> deleteById(Long id);
    Mono<Boolean> existByName(String name);
}