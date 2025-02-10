//package com.pragma.tecnologia.infrastructure.entrypoints;
//
//import com.pragma.tecnologia.domain.model.Tecnologia;
//import com.pragma.tecnologia.domain.usecase.TecnologiaUseCase;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@WebFluxTest(TecnologiaController.class)
//class TecnologiaControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private TecnologiaUseCase tecnologiaUseCase;
//
//    private Tecnologia tecnologia;
//
//    @BeforeEach
//    void setUp() {
//        tecnologia = new Tecnologia("Java", "Lenguaje de programación");
//    }
//
//    @Test
//    void testRegistrarTecnologia() {
//        Mockito.when(tecnologiaUseCase.crearTecnologia(Mockito.any(Tecnologia.class)))
//                .thenReturn(Mono.just(tecnologia));
//
//        webTestClient.post().uri("/tecnologias")
//                .bodyValue(tecnologia)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Tecnologia.class)
//                .isEqualTo(tecnologia);
//    }
//
//    @Test
//    void testListarTecnologias() {
//        Mockito.when(tecnologiaUseCase.listarTecnologias())
//                .thenReturn(Flux.just(tecnologia));
//
//        webTestClient.get().uri("/tecnologias")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Tecnologia.class)
//                .hasSize(1)
//                .contains(tecnologia);
//    }
//
//    @Test
//    void testObtenerTecnologiaPorId() {
//        Mockito.when(tecnologiaUseCase.obtenerTecnologiaPorId(1L))
//                .thenReturn(Mono.just(tecnologia));
//
//        webTestClient.get().uri("/tecnologias/1")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Tecnologia.class)
//                .isEqualTo(tecnologia);
//    }
//
//    @Test
//    void testEliminarTecnologia() {
//        Mockito.when(tecnologiaUseCase.eliminarTecnologia(1L))
//                .thenReturn(Mono.empty());
//
//        webTestClient.delete().uri("/tecnologias/1")
//                .exchange()
//                .expectStatus().isOk();
//    }
//}
