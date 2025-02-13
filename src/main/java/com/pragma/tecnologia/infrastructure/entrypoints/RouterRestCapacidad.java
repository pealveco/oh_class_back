package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.infrastructure.entrypoints.handler.CapacidadHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestCapacidad {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionCapacidad(CapacidadHandlerImpl capacidadHandler) {
        return route(POST("/capacidad"), capacidadHandler::createCapacidad)
                .andRoute(GET("/capacidad"), capacidadHandler::getAllCapacidades)
                .andRoute(GET("/capacidadby"), capacidadHandler::getAllCapacidadesBy);
    }
}