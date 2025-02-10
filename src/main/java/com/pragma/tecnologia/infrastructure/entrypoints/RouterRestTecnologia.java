package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.infrastructure.entrypoints.handler.TecnologiaHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestTecnologia {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionTecnologia(TecnologiaHandlerImpl tecnologiaHandler) {
        return route(POST("/tecnologia"), tecnologiaHandler::createTecnologia)
                .andRoute(GET("/tecnologia"), tecnologiaHandler::getAllTecnologias)
                .andRoute(GET("/tecnologia/{id}"), tecnologiaHandler::getTecnologiaById)
                .andRoute(DELETE("/tecnologia/{id}"), tecnologiaHandler::deleteTecnologia)
                .andRoute(PUT("/tecnologia/{id}"), tecnologiaHandler::updateTecnologia);
    }
}