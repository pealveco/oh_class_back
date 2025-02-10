// TecnologiaPersistencePort.java
package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TecnologiaPersistencePort {
    Mono<Tecnologia> save(Tecnologia tecnologia);
    Flux<Tecnologia> findAll();
    Mono<Tecnologia> findById(Long id);
    Mono<Void> deleteById(Long id);
    Mono<Boolean> existByName(String name);
}