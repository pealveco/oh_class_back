package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository;

import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.TecnologiaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TecnologiaRepository extends ReactiveCrudRepository<TecnologiaEntity, Long> {
    Flux<TecnologiaEntity> findByName(String name);
    Flux<TecnologiaEntity> findAllBy(Pageable pageable);
}