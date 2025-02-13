package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
import com.pragma.tecnologia.domain.enums.TechnicalMessage;
import com.pragma.tecnologia.domain.exceptions.BusinessException;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class TecnologiaUseCase implements TecnologiaServicePort {

    private final TecnologiaPersistencePort tecnologiaPersistencePort;

    @Override
    public Mono<Tecnologia> registerTecnologia(Tecnologia tecnologia) {
        return tecnologiaPersistencePort.existByName(tecnologia.name())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.TECNOLOGIA_ALREADY_EXISTS)))
                .flatMap(exists -> tecnologiaPersistencePort.save(tecnologia));
    }

    @Override
    public Flux<Tecnologia> getAllTecnologias() {
        return tecnologiaPersistencePort.findAll()
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.NO_DATA_FOUND)));
    }

    @Override
    public Flux<Tecnologia> getAllTecnologias(Pageable pageable) {
        return tecnologiaPersistencePort.findAll(pageable)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.NO_DATA_FOUND)));
    }

    @Override
    public Mono<Tecnologia> getTecnologiaById(Long id) {
        log.info("Buscando tecnología con ID: {}", id);

        return tecnologiaPersistencePort.findById(id)
                .doOnNext(tecnologia -> log.info("Tecnología encontrada: {}", tecnologia))
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.INVALID_MESSAGE_ID)));
    }

    @Override
    public Mono<Void> deleteTecnologiaById(Long id) {
        return tecnologiaPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.INVALID_MESSAGE_ID)))
                .flatMap(tecnologia -> tecnologiaPersistencePort.deleteById(id));
    }

    @Override
    public Mono<Tecnologia> updateTecnologia(Tecnologia tecnologia) {
        return tecnologiaPersistencePort.findById(tecnologia.id())
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.INVALID_MESSAGE_ID)))
                .flatMap(existingTecnologia -> tecnologiaPersistencePort.save(tecnologia));
    }
}