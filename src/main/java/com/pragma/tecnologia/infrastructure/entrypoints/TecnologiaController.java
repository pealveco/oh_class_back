//package com.pragma.tecnologia.infrastructure.entrypoints;
//
//import com.pragma.tecnologia.domain.model.Tecnologia;
//import com.pragma.tecnologia.domain.usecase.TecnologiaUseCase;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequestMapping("/tecnologias")
//public class TecnologiaController {
//    private final TecnologiaUseCase tecnologiaUseCase;
//
//    public TecnologiaController(TecnologiaUseCase tecnologiaUseCase) {
//        this.tecnologiaUseCase = tecnologiaUseCase;
//    }
//
//    @PostMapping
//    public Mono<Tecnologia> registrarTecnologia(@RequestBody Tecnologia tecnologia) {
//        return tecnologiaUseCase.crearTecnologia(tecnologia);
//    }
//
//    @GetMapping
//    public Flux<Tecnologia> listarTecnologias() {
//        return tecnologiaUseCase.listarTecnologias();
//    }
//
//    @GetMapping("/{id}")
//    public Mono<Tecnologia> obtenerTecnologia(@PathVariable Long id) {
//        return tecnologiaUseCase.obtenerTecnologiaPorId(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public Mono<Void> eliminarTecnologia(@PathVariable Long id) {
//        return tecnologiaUseCase.eliminarTecnologia(id);
//    }
//}