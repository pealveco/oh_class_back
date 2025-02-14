package com.pragma.capacidad.domain.usecase;

import com.pragma.capacidad.domain.api.CapacidadServicePort;
import com.pragma.capacidad.domain.enums.TechnicalMessage;
import com.pragma.capacidad.domain.exceptions.BusinessException;
import com.pragma.capacidad.domain.model.Capacidad;
import com.pragma.capacidad.domain.spi.CapacidadPersistencePort;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.mapper.CapacidadEntityMapper;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.repository.CapacidadRepository;
import com.pragma.tecnologia.domain.model.Tecnologia;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class CapacidadUseCase implements CapacidadServicePort {

    private final CapacidadPersistencePort capacidadPersistencePort;
    private final CapacidadRepository capacidadRepository;
    private final CapacidadEntityMapper capacidadEntityMapper;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Capacidad> registerCapacidad(Capacidad capacidad) {
        if (capacidad.tecnologiaIds().size() < 3 || capacidad.tecnologiaIds().size() > 20) {
            return Mono.error(new IllegalArgumentException("La capacidad debe tener entre 3 y 20 tecnologías"));
        }

        return checkTecnologiasExistence(capacidad.tecnologiaIds())
                .then(capacidadPersistencePort.existByName(capacidad.name())
                        .filter(exists -> !exists)
                        .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.CAPACIDAD_ALREADY_EXISTS)))
                        .flatMap(exists -> capacidadPersistencePort.save(capacidad))
                );
    }

    @Override
    public Flux<Capacidad> getAllCapacidades(Pageable pageable) {
        return capacidadPersistencePort.findAll(pageable)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error("BusinessException: {}", TechnicalMessage.CAPACIDADES_NOT_FOUND.getMessage());
                    return Flux.empty();
                }));
    }

    @Override
    public Flux<Capacidad> getAllCapacidades() {
        return capacidadPersistencePort.findAll()
                .switchIfEmpty(Flux.defer(() -> {
                    log.error("BusinessException: {}", TechnicalMessage.CAPACIDADES_NOT_FOUND.getMessage());
                    return Flux.empty();
                }));
    }

    @Override
    public Mono<Capacidad> getCapacidadById(Long id) {
        return capacidadPersistencePort.findById(id)
                .doOnNext(capacidad -> log.info("Capacidad encontrada: {}", capacidad))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("BusinessException: {}", TechnicalMessage.CAPACIDAD_NOT_FOUND.getMessage());
                    return Mono.empty();
                }));
    }

    // Metodos auxiliares

    /**
     * Valida que todos los IDs de tecnologías proporcionados existan en el servicio externo.
     *
     * @param tecnologiaIds Conjunto de IDs de tecnologías a validar.
     * @return Un `Mono<Void>` que completa si todas las tecnologías existen, o emite un error si alguna no existe.
     */
    private Mono<Void> checkTecnologiasExistence(Set<Long> tecnologiaIds) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8080").build();

        return Flux.fromIterable(tecnologiaIds)
                .flatMap(id -> webClient.get()
                        .uri("/tecnologia/{id}", id)
                        .retrieve()
                        .bodyToMono(Tecnologia.class)
                        .doOnNext(tecnologia -> log.info("Tecnología encontrada ***: {}", tecnologia))
                        .onErrorResume(e -> Mono.empty()))
                .collectList()
                .flatMap(tecnologias -> {
                    if (tecnologias.size() != tecnologiaIds.size()) {
                        return Mono.error(new BusinessException(TechnicalMessage.INVALID_TECHNOLOGIES));
                    }
                    return Mono.empty();
                });
    }
}