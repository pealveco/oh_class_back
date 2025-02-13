package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Capacidad;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CapacidadServicePort {
    Mono<Capacidad> registerCapacidad(Capacidad capacidad);
    Flux<Capacidad> getAllCapacidades(Pageable pageable);
    Flux<Capacidad> getAllCapacidadesBy(Pageable pageable);
}