package com.pragma.tecnologia.infrastructure.entrypoints.handler;

import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.RouterRestTecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.TecnologiaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TecnologiaHandlerImplTest {

    private TecnologiaHandlerImpl handler;
    private TecnologiaServicePort tecnologiaServicePort;
    private TecnologiaMapper tecnologiaMapper;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        tecnologiaServicePort = mock(TecnologiaServicePort.class);
        tecnologiaMapper = mock(TecnologiaMapper.class);
        handler = new TecnologiaHandlerImpl(tecnologiaServicePort, tecnologiaMapper);
        RouterFunction<ServerResponse> routerFunction = new RouterRestTecnologia().routerFunctionTecnologia(handler);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void createTecnologia() {
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Java description");
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");

        when(tecnologiaMapper.tecnologiaDTOToTecnologia(any())).thenReturn(tecnologia);
        when(tecnologiaServicePort.registerTecnologia(any())).thenReturn(Mono.just(tecnologia));

        webTestClient.post().uri("/tecnologia")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Tecnologia.class)
                .isEqualTo(tecnologia);
    }

    @Test
    void getAllTecnologias() {
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Java description");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        when(tecnologiaServicePort.getAllTecnologias(pageable)).thenReturn(Flux.just(tecnologia));
        when(tecnologiaMapper.tecnologiaToTecnologiaDTO(tecnologia)).thenReturn(dto);

        webTestClient.get().uri("/tecnologia?page=0&size=10&sort=asc")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TecnologiaDTO.class)
                .contains(dto);
    }

    @Test
    void getTecnologiaById() {
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Java description");

        when(tecnologiaServicePort.getTecnologiaById(1L)).thenReturn(Mono.just(tecnologia));
        when(tecnologiaMapper.tecnologiaToTecnologiaDTO(tecnologia)).thenReturn(dto);

        webTestClient.get().uri("/tecnologia/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(TecnologiaDTO.class)
                .isEqualTo(dto);
    }

    @Test
    void deleteTecnologia() {
        when(tecnologiaServicePort.deleteTecnologiaById(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/tecnologia/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updateTecnologia() {
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Java description");
        Tecnologia tecnologia = new Tecnologia(1L, "Java", "Java description");

        when(tecnologiaServicePort.getTecnologiaById(1L)).thenReturn(Mono.just(tecnologia));
        when(tecnologiaServicePort.updateTecnologia(any())).thenReturn(Mono.just(tecnologia));

        webTestClient.put().uri("/tecnologia/1")
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Tecnologia.class)
                .isEqualTo(tecnologia);
    }
}