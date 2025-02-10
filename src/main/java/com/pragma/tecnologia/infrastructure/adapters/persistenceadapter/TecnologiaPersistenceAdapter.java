package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.TecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.TecnologiaRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class TecnologiaPersistenceAdapter implements TecnologiaPersistencePort {
    private final TecnologiaRepository tecnologiaRepository;
    private final TecnologiaEntityMapper tecnologiaEntityMapper;

    @Override
    public Mono<Tecnologia> save(Tecnologia tecnologia) {
        return tecnologiaRepository.save(tecnologiaEntityMapper.toEntity(tecnologia))
                .map(tecnologiaEntityMapper::toModel);
    }

    @Override
    public Flux<Tecnologia> findAll() {
        return tecnologiaRepository.findAll()
                .map(tecnologiaEntityMapper::toModel);
    }

    @Override
    public Mono<Tecnologia> findById(Long id) {
        return tecnologiaRepository.findById(id)
                .map(tecnologiaEntityMapper::toModel);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return tecnologiaRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existByName(String name) {
        return tecnologiaRepository.findByName(name)
                .collectList()
                .map(list -> !list.isEmpty());
    }
}