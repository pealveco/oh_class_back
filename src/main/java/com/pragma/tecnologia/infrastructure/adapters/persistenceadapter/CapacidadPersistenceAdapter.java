package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter;

import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.spi.CapacidadPersistencePort;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.CapacidadEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.CapacidadRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CapacidadPersistenceAdapter implements CapacidadPersistencePort {

    private final CapacidadRepository capacidadRepository;
    private final CapacidadEntityMapper capacidadEntityMapper;

    @Override
    public Mono<Capacidad> save(Capacidad capacidad) {
        return capacidadRepository.save(capacidadEntityMapper.toEntity(capacidad))
                .map(capacidadEntityMapper::toModel);
    }

    @Override
    public Flux<Capacidad> findAll() {
        return capacidadRepository.findAll()
                .map(capacidadEntityMapper::toModel);
    }

    @Override
    public Flux<Capacidad> findAll(Pageable pageable) {
        return capacidadRepository.findAllBy(pageable)
                .map(capacidadEntityMapper::toModel);
    }

    @Override
    public Mono<Capacidad> findById(Long id) {
        return capacidadRepository.findById(id)
                .map(capacidadEntityMapper::toModel);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return capacidadRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existByName(String name) {
        return capacidadRepository.findByName(name)
                .collectList()
                .map(list -> !list.isEmpty());
    }
}