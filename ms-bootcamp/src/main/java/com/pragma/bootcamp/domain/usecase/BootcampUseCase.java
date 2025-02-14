package com.pragma.bootcamp.domain.usecase;

import com.pragma.bootcamp.domain.api.BootcampServicePort;
import com.pragma.bootcamp.domain.enums.TechnicalMessage;
import com.pragma.bootcamp.domain.exceptions.BusinessException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.spi.BootcampPersistencePort;
import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.mapper.BootcampEntityMapper;
import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.repository.BootcampRepository;
import com.pragma.capacidad.domain.model.Capacidad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class BootcampUseCase implements BootcampServicePort {

    private final BootcampPersistencePort bootcampPersistencePort;
    private final BootcampRepository bootcampRepository;
    private final BootcampEntityMapper bootcampEntityMapper;
    private final WebClient.Builder webClientBuilder;

    public Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp) {
        if (bootcamp.name() == null || bootcamp.name().isEmpty()) {
            return Mono.error(new IllegalArgumentException("El nombre del bootcamp no puede estar vacío"));
        }
        return checkCapacidadesExistence(bootcamp.capacidadIds())
                .then(bootcampPersistencePort.existsByName(bootcamp.name())
                        .filter(exists -> !exists)
                        .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BOOTCAMP_ALREADY_EXISTS)))
                        .flatMap(exists -> bootcampPersistencePort.save(bootcamp))
                );
    }

    public Flux<Bootcamp> getAllBootcamps(Pageable pageable) {
        return bootcampPersistencePort.findAll(pageable)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error("BusinessException: {}", TechnicalMessage.BOOTCAMPS_NOT_FOUND.getMessage());
                    return Flux.empty();
                }))
                .onErrorResume(e -> {
                    log.error("Error al obtener todos los bootcamps con paginación", e);
                    return Flux.error(new BusinessException(TechnicalMessage.GET_ALL_ERROR));
                });
    }

    /**
     * Valida que todos los IDs de capacidades proporcionados existan en el servicio externo.
     *
     * @param capacidadIds Conjunto de IDs de capacidades a validar.
     * @return Un `Mono<Void>` que completa si todas las capacidades existen, o emite un error si alguna no existe.
     */
    private Mono<Void> checkCapacidadesExistence(Set<Long> capacidadIds) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8081").build();

        return Flux.fromIterable(capacidadIds)
                .flatMap(id -> webClient.get()
                        .uri("/capacidad/{id}", id)
                        .retrieve()
                        .bodyToMono(Capacidad.class)
                        .doOnNext(capacidad -> log.info("Capacidad encontrada: {}", capacidad))
                        .onErrorResume(e -> Mono.empty()))
                .collectList()
                .flatMap(capacidades -> {
                    if (capacidades.size() != capacidadIds.size()) {
                        return Mono.error(new BusinessException(TechnicalMessage.INVALID_CAPACIDADES));
                    }
                    return Mono.empty();
                });
    }
}