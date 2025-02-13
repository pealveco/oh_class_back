package com.pragma.bootcamp.infrastructure.entrypoints;

import com.pragma.bootcamp.infrastructure.entrypoints.handler.BootcampHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRestBootcamp {
    @Bean
    public RouterFunction<ServerResponse> routerFunctionBootcamp(BootcampHandlerImpl bootcampHandler) {
        return route(POST("/bootcamp"), bootcampHandler::createBootcamp)
                .andRoute(GET("/bootcamp"), bootcampHandler::getAllBootcamps);
    }
}