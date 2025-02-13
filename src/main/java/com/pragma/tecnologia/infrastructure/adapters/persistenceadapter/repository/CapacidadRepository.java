package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository;

import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.CapacidadEntity;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.TecnologiaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CapacidadRepository extends ReactiveCrudRepository<CapacidadEntity, Long> {
    Flux<CapacidadEntity> findByName(String name);
    Flux<CapacidadEntity> findAllBy(Pageable pageable);
}