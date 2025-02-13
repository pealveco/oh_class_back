package com.pragma.bootcamp.infrastructure.adapters.persistenceadapter;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.spi.BootcampPersistencePort;
import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.mapper.BootcampEntityMapper;
import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.repository.BootcampRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class BootcampPersistenceAdapter implements BootcampPersistencePort {

    private final BootcampRepository bootcampRepository;
    private final BootcampEntityMapper bootcampEntityMapper;

    @Override
    public Mono<Bootcamp> save(Bootcamp bootcamp) {
        return bootcampRepository.save(bootcampEntityMapper.toEntity(bootcamp))
                .map(bootcampEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return bootcampRepository.existsByName(name);
    }

    @Override
    public Flux<Bootcamp> findAll(Pageable pageable) {
        return bootcampRepository.findAllBy(pageable)
                .map(bootcampEntityMapper::toModel);
    }
}