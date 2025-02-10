package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
import com.pragma.tecnologia.domain.enums.TechnicalMessage;
import com.pragma.tecnologia.domain.exceptions.BusinessException;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.TecnologiaPersistencePort;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.TecnologiaEntity;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper.TecnologiaEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TecnologiaUseCase implements TecnologiaServicePort {

    private final TecnologiaPersistencePort tecnologiaPersistencePort;
    private final TecnologiaEntityMapper tecnologiaEntityMapper;

    @Override
    public Mono<Tecnologia> registerTecnologia(Tecnologia tecnologia) {
        return tecnologiaPersistencePort.save(tecnologia);
    }

//    public Flux<Tecnologia> listarTecnologias() {
//        return tecnologiaPersistencePort.findAll()
//                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.INTERNAL_ERROR)));
//    }
//
//    public Mono<Tecnologia> obtenerTecnologiaPorId(Long id) {
//        return tecnologiaPersistencePort.findById(id)
//                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.INVALID_MESSAGE_ID)));
//    }
//
//    public Mono<Void> eliminarTecnologia(Long id) {
//        return tecnologiaPersistencePort.findById(id)
//                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.INVALID_MESSAGE_ID)))
//                .flatMap(tecnologia -> tecnologiaPersistencePort.deleteById(id));
//    }
}