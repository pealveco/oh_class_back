package com.pragma.bootcamp.domain.usecase;

import com.pragma.bootcamp.domain.api.BootcampServicePort;
import com.pragma.bootcamp.domain.enums.TechnicalMessage;
import com.pragma.bootcamp.domain.exceptions.BusinessException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.spi.BootcampPersistencePort;
import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.mapper.BootcampEntityMapper;
import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.repository.BootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BootcampUseCase implements BootcampServicePort {

    private final BootcampPersistencePort bootcampPersistencePort;
    private final BootcampRepository bootcampRepository;
    private final BootcampEntityMapper bootcampEntityMapper;

    public Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp) {
        if (bootcamp.name() == null || bootcamp.name().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El nombre del bootcamp no puede estar vacío"));
        }
        return bootcampPersistencePort.existsByName(bootcamp.name())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BOOTCAMP_ALREADY_EXISTS)))
                .flatMap(exists -> bootcampPersistencePort.save(bootcamp));
    }

    public Flux<Bootcamp> getAllBootcamps(Pageable pageable) {
        return bootcampPersistencePort.findAll(pageable)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BOOTCAMPS_NOT_FOUND)));
    }
}