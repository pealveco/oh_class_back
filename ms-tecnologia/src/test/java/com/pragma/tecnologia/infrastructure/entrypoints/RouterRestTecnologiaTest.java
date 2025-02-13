package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.infrastructure.entrypoints.handler.TecnologiaHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(RouterRestTecnologia.class)
class RouterRestTecnologiaTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TecnologiaHandlerImpl tecnologiaHandler;

    private RouterFunction<ServerResponse> routerFunction;

    @BeforeEach
    void setUp() {
        routerFunction = new RouterRestTecnologia().routerFunctionTecnologia(tecnologiaHandler);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    void testCreateTecnologia() {
        // Configura el mock
        when(tecnologiaHandler.createTecnologia(any())).thenReturn(ServerResponse.ok().build());

        // Realiza la prueba
        webTestClient.post().uri("/tecnologia")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetAllTecnologias() {
        // Configura el mock
        when(tecnologiaHandler.getAllTecnologias(any())).thenReturn(ServerResponse.ok().build());

        // Realiza la prueba
        webTestClient.get().uri("/tecnologia")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetTecnologiaById() {
        // Configura el mock
        when(tecnologiaHandler.getTecnologiaById(any())).thenReturn(ServerResponse.ok().build());

        // Realiza la prueba
        webTestClient.get().uri("/tecnologia/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteTecnologia() {
        // Configura el mock
        when(tecnologiaHandler.deleteTecnologia(any())).thenReturn(ServerResponse.ok().build());

        // Realiza la prueba
        webTestClient.delete().uri("/tecnologia/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }
    @Test
    void testUpdateTecnologia() {
        // Configura el mock
        when(tecnologiaHandler.updateTecnologia(any())).thenReturn(ServerResponse.ok().build());

        // Realiza la prueba
        webTestClient.put().uri("/tecnologia/{id}", 1)
                .exchange()
                .expectStatus().isOk();
    }
}