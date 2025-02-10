package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface TecnologiaServicePort {
    Mono<Tecnologia> registerTecnologia(Tecnologia tecnologia);
}