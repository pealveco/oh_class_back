package com.pragma.capacidad.infrastructure.adapters.persistenceadapter.repository;

import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.entity.CapacidadEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CapacidadRepository extends ReactiveCrudRepository<CapacidadEntity, Long> {
    Flux<CapacidadEntity> findByName(String name);
    Flux<CapacidadEntity> findAllBy(Pageable pageable);
}