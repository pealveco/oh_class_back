package com.pragma.tecnologia.infrastructure.entrypoints.handler;

        import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
        import com.pragma.tecnologia.domain.model.Tecnologia;
        import com.pragma.tecnologia.domain.usecase.TecnologiaUseCase;
        import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
        import com.pragma.tecnologia.infrastructure.entrypoints.mapper.TecnologiaMapper;
        import lombok.RequiredArgsConstructor;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.stereotype.Component;
        import org.springframework.web.reactive.function.server.ServerRequest;
        import org.springframework.web.reactive.function.server.ServerResponse;
        import reactor.core.publisher.Mono;

        @Component
        @RequiredArgsConstructor
        @Slf4j
        public class TecnologiaHandlerImpl {

            private final TecnologiaServicePort tecnologiaServicePort;
            private final TecnologiaMapper tecnologiaMapper;
            private final TecnologiaUseCase tecnologiaUseCase;

            public Mono<ServerResponse> createTecnologia(ServerRequest request) {
                return request.bodyToMono(Tecnologia.class)
                        .flatMap(tecnologia -> ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(tecnologiaUseCase.registerTecnologia(tecnologia), Tecnologia.class));
//                        .doOnNext(tecnologiaDTO -> System.out.println("TecnologiaHandlerImpl1: " + tecnologiaDTO))
//                        .flatMap(tecnologiaDTO -> tecnologiaServicePort.registerTecnologia(tecnologiaMapper.tecnologiaDTOToTecnologia(tecnologiaDTO))
//                                .doOnSuccess(savedTecnologia -> log.info("Tecnologia created successfully"))
//                        )
//                        .flatMap(tecnologia -> ServerResponse
//                                .status(HttpStatus.CREATED)
//                                .bodyValue("Tecnologia created successfully"))
//                        .doOnError(ex -> log.error("Error creating tecnologia", ex))
//                        .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue("Internal Server Error"));
            }

//            public Mono<ServerResponse> getAllTecnologias(ServerRequest request) {
//                return ServerResponse.ok().body(tecnologiaServicePort.listarTecnologias(), TecnologiaDTO.class);
//            }
//
//            public Mono<ServerResponse> getTecnologiaById(ServerRequest request) {
//                Long id = Long.valueOf(request.pathVariable("id"));
//                return tecnologiaServicePort.obtenerTecnologiaPorId(id)
//                        .flatMap(tecnologia -> ServerResponse.ok().bodyValue(tecnologiaMapper.tecnologiaToTecnologiaDTO(tecnologia)))
//                        .switchIfEmpty(ServerResponse.notFound().build());
//            }
//
//            public Mono<ServerResponse> deleteTecnologia(ServerRequest request) {
//                Long id = Long.valueOf(request.pathVariable("id"));
//                return tecnologiaServicePort.eliminarTecnologia(id)
//                        .then(ServerResponse.noContent().build())
//                        .switchIfEmpty(ServerResponse.notFound().build());
//            }
        }