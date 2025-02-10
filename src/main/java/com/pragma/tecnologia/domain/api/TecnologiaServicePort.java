package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TecnologiaServicePort {
    Mono<Tecnologia> registerTecnologia(Tecnologia tecnologia);
    Flux<Tecnologia> getAllTecnologias();
    Mono<Tecnologia> getTecnologiaById(Long id);
    Mono<Void> deleteTecnologiaById(Long id);
    Mono<Tecnologia> updateTecnologia(Tecnologia tecnologia);
}