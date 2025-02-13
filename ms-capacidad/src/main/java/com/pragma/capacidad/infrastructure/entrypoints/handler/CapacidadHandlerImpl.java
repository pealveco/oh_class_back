package com.pragma.capacidad.infrastructure.entrypoints.handler;

import com.pragma.capacidad.domain.api.CapacidadServicePort;
import com.pragma.capacidad.domain.model.Capacidad;
import com.pragma.capacidad.infrastructure.entrypoints.dto.CapacidadDTO;
import com.pragma.capacidad.infrastructure.entrypoints.mapper.CapacidadMapper;
import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.TecnologiaMapper;
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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class CapacidadHandlerImpl {

    private final CapacidadServicePort capacidadServicePort;
    private final CapacidadMapper capacidadMapper;
//    private final TecnologiaMapper tecnologiaMapper;
    private final TecnologiaServicePort tecnologiaServicePort;

    public Mono<ServerResponse> createCapacidad(ServerRequest request) {
        return request.bodyToMono(CapacidadDTO.class)
                .flatMap(capacidadDTO -> {
                    Capacidad capacidad = capacidadMapper.capacidadDTOToCapacidad(capacidadDTO);
                    return capacidadServicePort.registerCapacidad(capacidad)
                            .flatMap(savedCapacidad -> ServerResponse.status(HttpStatus.CREATED).bodyValue(savedCapacidad));
                });
    }

    public Mono<ServerResponse> getAllCapacidades(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        String sort = request.queryParam("sort").orElse("asc");

        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "name"));

        return ServerResponse.ok().body(
            capacidadServicePort.getAllCapacidades(pageable)
                .map(capacidad -> {
                    CapacidadDTO dto = capacidadMapper.capacidadToCapacidadDTO(capacidad);
                    dto = new CapacidadDTO(capacidad.id(), dto.name(), dto.description(), dto.tecnologiaIds());
                    return dto;
                }),
            CapacidadDTO.class
        );
    }

    public Mono<ServerResponse> getAllCapacidadesBy(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        String sort = request.queryParam("sort").orElse("asc");
        String sortBy = request.queryParam("sortBy").orElse("name");

        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size);

        return capacidadServicePort.getAllCapacidades(pageable)
            .collectList()
            .flatMap(capacidades -> {
                if ("tecnologiaCount".equals(sortBy)) {
                    capacidades.sort((c1, c2) -> {
                        int size1 = c1.tecnologiaIds().size();
                        int size2 = c2.tecnologiaIds().size();
                        return direction.isAscending() ? Integer.compare(size1, size2) : Integer.compare(size2, size1);
                    });
                }
                return Flux.fromIterable(capacidades)
                    .flatMap(capacidad -> fetchTecnologiasByIds(capacidad.tecnologiaIds())
                        .collectList()
                        .map(tecnologias -> {
                            CapacidadDTO dto = capacidadMapper.capacidadToCapacidadDTO(capacidad);
                            dto = new CapacidadDTO(
                                capacidad.id(),
                                dto.name(),
                                dto.description(),
                                tecnologias.stream()
                                    .map(Tecnologia::id)
                                    .collect(Collectors.toSet())
                            );
                            return dto;
                        }))
                    .collectList();
            })
            .flatMap(capacidadDTOs -> ServerResponse.ok().bodyValue(capacidadDTOs));
    }

    private Flux<Tecnologia> fetchTecnologiasByIds(Set<Long> ids) {
        return Flux.fromIterable(ids)
            .flatMap(tecnologiaServicePort::getTecnologiaById);
    }
}