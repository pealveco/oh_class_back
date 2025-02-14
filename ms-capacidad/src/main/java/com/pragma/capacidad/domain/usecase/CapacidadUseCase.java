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

        return validateTecnologias(capacidad.tecnologiaIds())
                .then(capacidadPersistencePort.existByName(capacidad.name())
                        .filter(exists -> !exists)
                        .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.CAPACIDAD_ALREADY_EXISTS)))
                        .flatMap(exists -> capacidadPersistencePort.save(capacidad)));
    }

    @Override
    public Flux<Capacidad> getAllCapacidades(Pageable pageable) {
        return capacidadPersistencePort.findAll(pageable)
                .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.NO_DATA_FOUND)));
    }

    @Override
    public Flux<Capacidad> getAllCapacidades() {
        return capacidadPersistencePort.findAll();
    }

    // Metodos auxiliares
    private Mono<Void> validateTecnologias(Set<Long> tecnologiaIds) {
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); // Cambia la URL según sea necesario

        return Flux.fromIterable(tecnologiaIds)
                .flatMap(id -> webClient.get()
                        .uri("/tecnologia/{id}", id)
                        .retrieve()
                        .bodyToMono(Tecnologia.class)
                        .doOnNext(tecnologia -> log.info("Tecnología encontrada ***: {}", tecnologia))
                        .onErrorResume(e -> Mono.empty())) // Maneja el error si la tecnología no existe
                .collectList()
                .flatMap(tecnologias -> {
                    if (tecnologias.size() != tecnologiaIds.size()) {
                        return Mono.error(new BusinessException(TechnicalMessage.INVALID_TECHNOLOGIES));
                    }
                    return Mono.empty();
                });
    }
}