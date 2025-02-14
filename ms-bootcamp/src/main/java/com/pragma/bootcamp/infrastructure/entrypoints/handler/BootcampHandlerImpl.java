package com.pragma.bootcamp.infrastructure.entrypoints.handler;

import com.pragma.bootcamp.domain.api.BootcampServicePort;
import com.pragma.bootcamp.domain.exceptions.BusinessException;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.capacidad.domain.api.CapacidadServicePort;
import com.pragma.capacidad.domain.model.Capacidad;
import com.pragma.bootcamp.infrastructure.entrypoints.dto.BootcampDTO;
import com.pragma.bootcamp.infrastructure.entrypoints.mapper.BootcampMapper;
import com.pragma.capacidad.infrastructure.entrypoints.dto.CapacidadDTO;
import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
import com.pragma.bootcamp.domain.enums.TechnicalMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class BootcampHandlerImpl {

    private final BootcampServicePort bootcampServicePort;
    private final BootcampMapper bootcampMapper;
    private final TecnologiaServicePort tecnologiaServicePort;
    private final CapacidadServicePort capacidadServicePort;

    public Mono<ServerResponse> createBootcamp(ServerRequest request) {
        return request.bodyToMono(BootcampDTO.class)
                .flatMap(bootcampDTO -> {
                    if (bootcampDTO.capacidadIds().size() < 1 || bootcampDTO.capacidadIds().size() > 4) {
                        return Mono.error(new IllegalArgumentException("El bootcamp debe tener entre 1 y 4 capacidadIds"));
                    }
                    Bootcamp bootcamp = bootcampMapper.bootcampDTOToBootcamp(bootcampDTO);
                    return validateCapacidades(bootcamp.capacidadIds())
                            .then(bootcampServicePort.registerBootcamp(bootcamp))
                            .flatMap(savedBootcamp -> ServerResponse.status(HttpStatus.CREATED).bodyValue(savedBootcamp));
                });
    }

    public Mono<ServerResponse> getAllBootcamps(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        String sort = request.queryParam("sort").orElse("asc");
        String sortBy = request.queryParam("sortBy").orElse("name");

        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size);

        return bootcampServicePort.getAllBootcamps(pageable)
                .collectList()
                .flatMap(bootcamps -> {
                    if ("capacidadCount".equals(sortBy)) {
                        bootcamps.sort((b1, b2) -> {
                            int size1 = b1.capacidadIds().size();
                            int size2 = b2.capacidadIds().size();
                            return direction.isAscending() ? Integer.compare(size1, size2) : Integer.compare(size2, size1);
                        });
                    } else {
                        bootcamps.sort((b1, b2) -> {
                            int comparison = b1.name().compareTo(b2.name());
                            if (comparison == 0) {
                                comparison = b1.id().compareTo(b2.id());
                            }
                            return direction.isAscending() ? comparison : -comparison;
                        });
                    }
                    return Flux.fromIterable(bootcamps)
                        .flatMap(bootcamp -> fetchCapacidadesByIds(bootcamp.capacidadIds())
                                .collectList()
                                .map(capacidades -> {
                                    BootcampDTO dto = bootcampMapper.bootcampToBootcampDTO(bootcamp);
                                    dto = new BootcampDTO(
                                            bootcamp.id(),
                                            dto.name(),
                                            dto.description(),
                                            capacidades.stream()
                                                    .map(Capacidad::id)
                                                    .collect(Collectors.toSet())
                                    );
                                    return dto;
                                }))
                        .collectList();
                })
                .flatMap(bootcampDTOs -> ServerResponse.ok().bodyValue(bootcampDTOs));
    }

    // Metodos auxiliares
    private Flux<Capacidad> fetchCapacidadesByIds(Set<Long> ids) {
        return Flux.fromIterable(ids)
                .flatMap(capacidadServicePort::getCapacidadById);
    }

    private Mono<Void> validateCapacidades(Set<Long> capacidadIds) {
        return Flux.fromIterable(capacidadIds)
                .flatMap(capacidadServicePort::getCapacidadById)
                .collectList()
                .flatMap(capacidades -> {
                    if (capacidades.size() != capacidadIds.size()) {
                        return Mono.error(new BusinessException(TechnicalMessage.INVALID_CAPACIDADES));
                    }
                    return Mono.empty();
                });
    }
}