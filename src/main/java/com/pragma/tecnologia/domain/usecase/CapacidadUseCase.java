package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.CapacidadServicePort;
import com.pragma.tecnologia.domain.enums.TechnicalMessage;
import com.pragma.tecnologia.domain.exceptions.BusinessException;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.spi.CapacidadPersistencePort;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.CapacidadEntityMapper;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.repository.CapacidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CapacidadUseCase implements CapacidadServicePort {

    private final CapacidadPersistencePort capacidadPersistencePort;
    private final CapacidadRepository capacidadRepository;
    private final CapacidadEntityMapper capacidadEntityMapper;

    @Override
    public Mono<Capacidad> registerCapacidad(Capacidad capacidad) {
        if (capacidad.tecnologiaIds().size() < 3 || capacidad.tecnologiaIds().size() > 20) {
            return Mono.error(new IllegalArgumentException("La capacidad debe tener entre 3 y 20 tecnologías"));
        }

        return capacidadPersistencePort.existByName(capacidad.name())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.CAPACIDAD_ALREADY_EXISTS)))
                .flatMap(exists -> capacidadPersistencePort.save(capacidad));
    }

    @Override
    public Flux<Capacidad> getAllCapacidades(Pageable pageable) {
        return capacidadPersistencePort.findAll(pageable)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.NO_DATA_FOUND)));
    }

    @Override
    public Flux<Capacidad> getAllCapacidadesBy(Pageable pageable) {
        return capacidadRepository.findAllBy(pageable)
                .map(capacidadEntityMapper::toModel);
    }
}