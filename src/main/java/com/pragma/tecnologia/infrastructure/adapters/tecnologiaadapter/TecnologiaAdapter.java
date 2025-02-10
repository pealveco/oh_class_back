package com.pragma.tecnologia.infrastructure.adapters.tecnologiaadapter;

import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.TecnologiaEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.repository.TecnologiaRepository;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.TecnologiaEntityMapper;

@Service
public class TecnologiaAdapter implements TecnologiaPersistencePort {
    private final TecnologiaRepository tecnologiaRepository;
    private final TecnologiaEntityMapper tecnologiaEntityMapper;

    public TecnologiaAdapter(TecnologiaRepository tecnologiaRepository, TecnologiaEntityMapper tecnologiaEntityMapper) {
        this.tecnologiaRepository = tecnologiaRepository;
        this.tecnologiaEntityMapper = tecnologiaEntityMapper;
    }

    @Override
    public Mono<Tecnologia> save(Tecnologia tecnologia) {
        TecnologiaEntity entity = tecnologiaEntityMapper.toEntity(tecnologia);
        return tecnologiaRepository.save(entity)
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
        return tecnologiaRepository.existsByName(name);
    }
}