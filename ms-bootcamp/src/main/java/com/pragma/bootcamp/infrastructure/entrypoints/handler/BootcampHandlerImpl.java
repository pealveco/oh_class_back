package com.pragma.bootcamp.infrastructure.entrypoints.handler;

import com.pragma.bootcamp.domain.api.BootcampServicePort;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.capacidad.domain.model.Capacidad;
import com.pragma.bootcamp.infrastructure.entrypoints.dto.BootcampDTO;
import com.pragma.bootcamp.infrastructure.entrypoints.mapper.BootcampMapper;
import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
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
import com.pragma.tecnologia.domain.model.Tecnologia;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class BootcampHandlerImpl {

    private final BootcampServicePort bootcampServicePort;
    private final BootcampMapper bootcampMapper;
    private final TecnologiaServicePort tecnologiaServicePort;

    public Mono<ServerResponse> createBootcamp(ServerRequest request) {
        return request.bodyToMono(BootcampDTO.class)
                .flatMap(bootcampDTO -> {
                    Bootcamp bootcamp = bootcampMapper.bootcampDTOToBootcamp(bootcampDTO);
                    return bootcampServicePort.registerBootcamp(bootcamp)
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
                    .flatMap(bootcamp -> Flux.fromIterable(bootcamp.capacidadIds())
                        .flatMap(capacidadId -> fetchTecnologiasByIds(Set.of(capacidadId))
                            .collectList()
                            .map(tecnologias -> new Capacidad(capacidadId, "Capacidad " + capacidadId, "Descripción " + capacidadId, tecnologias)))
                        .collectList()
                        .flatMapMany(capacidades -> Flux.fromIterable(capacidades)
                            .map(capacidad -> new Bootcamp(bootcamp.id(), bootcamp.name(), bootcamp.description(), capacidades.stream().map(Capacidad::id).collect(Collectors.toList())))))
                    .collectList()
                    .flatMapMany(Flux::fromIterable);
            })
            .flatMap(bootcampDTOs -> ServerResponse.ok().bodyValue(bootcampDTOs));
    }

    private Flux<Tecnologia> fetchTecnologiasByIds(Set<Long> ids) {
        return Flux.fromIterable(ids)
            .flatMap(tecnologiaServicePort::getTecnologiaById);
    }
}