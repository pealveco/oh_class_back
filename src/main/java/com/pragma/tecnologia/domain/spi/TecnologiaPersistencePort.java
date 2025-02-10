package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Tecnologia;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TecnologiaPersistencePort {
    Mono<Tecnologia> save(Tecnologia tecnologia);
    Flux<Tecnologia> findAll();
    Flux<Tecnologia> findAll(Pageable pageable);
    Mono<Tecnologia> findById(Long id);
    Mono<Void> deleteById(Long id);
    Mono<Boolean> existByName(String name);
}