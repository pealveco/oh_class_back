package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.handler.TecnologiaHandlerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@Tag(name = "Tecnologia", description = "API for Tecnologia management")
@Configuration
public class RouterRestTecnologia {

    @Autowired
    private TecnologiaHandlerImpl tecnologiaHandler;

    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/tecnologia",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = TecnologiaHandlerImpl.class,
                            operation = @Operation(
                                    operationId = "getAllTecnologias",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Get all tecnologias",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Tecnologia.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Tecnologias not found"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "500",
                                                    description = "Internal server error"
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/tecnologia",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = TecnologiaHandlerImpl.class,
                            operation = @Operation(
                                    operationId = "createTecnologia",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Create a tecnologia",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Tecnologia.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "400",
                                                    description = "Bad request"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "500",
                                                    description = "Internal server error"
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = Tecnologia.class
                                            ))
                                    )
                            )
                    ),
                    @RouterOperation(
                            path = "/tecnologia/{id}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = TecnologiaHandlerImpl.class,
                            operation = @Operation(
                                    operationId = "getTecnologiaById",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Get a tecnologia by id",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Tecnologia.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Tecnologia not found"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "500",
                                                    description = "Internal server error"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(
                                                    in = ParameterIn.PATH,
                                                    name = "id"
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/tecnologia/{id}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.PUT,
                            beanClass = TecnologiaHandlerImpl.class,
                            operation = @Operation(
                                    operationId = "updateTecnologia",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Update a tecnologia",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Tecnologia.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Tecnologia not found"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "500",
                                                    description = "Internal server error"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(
                                                    in = ParameterIn.PATH,
                                                    name = "id"
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/tecnologia/{id}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.DELETE,
                            beanClass = TecnologiaHandlerImpl.class,
                            operation = @Operation(
                                    operationId = "deleteTecnologia",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Delete a tecnologia",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Tecnologia.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Tecnologia not found"
                                            ),
                                            @ApiResponse(
                                                    responseCode = "500",
                                                    description = "Internal server error"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(
                                                    in = ParameterIn.PATH,
                                                    name = "id"
                                            )
                                    }
                            )
                    )
            }
    )
    @Bean
    public RouterFunction<ServerResponse> routerFunctionTecnologia(TecnologiaHandlerImpl tecnologiaHandler) {
        return route(POST("/tecnologia").and(accept(APPLICATION_JSON)), tecnologiaHandler::createTecnologia)
                .andRoute(GET("/tecnologia").and(accept(APPLICATION_JSON)), tecnologiaHandler::getAllTecnologias)
                .andRoute(GET("/tecnologia/{id}").and(accept(APPLICATION_JSON)), tecnologiaHandler::getTecnologiaById)
                .andRoute(DELETE("/tecnologia/{id}").and(accept(APPLICATION_JSON)), tecnologiaHandler::deleteTecnologia)
                .andRoute(PUT("/tecnologia/{id}").and(accept(APPLICATION_JSON)), tecnologiaHandler::updateTecnologia);
    }

//    @Bean
//    @RouterOperations(
//            {
//                    @RouterOperation(
//                            path = "/tecnologia",
//                            produces = {
//                                    MediaType.APPLICATION_JSON_VALUE
//                            },
//                            method = RequestMethod.GET,
//                            beanClass = TecnologiaHandlerImpl.class,
//                            operation = @Operation(
//                                    operationId = "getAllTecnologias",
//                                    responses = {
//                                            @ApiResponse(
//                                                    responseCode = "200",
//                                                    description = "Get all tecnologias",
//                                                    content = @Content(schema = @Schema(
//                                                            implementation = Tecnologia.class
//                                                    ))
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "404",
//                                                    description = "Tecnologias not found"
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "500",
//                                                    description = "Internal server error"
//                                            )
//                                    }
//                            )
//                    ),
//                    @RouterOperation(
//                            path = "/tecnologia",
//                            produces = {
//                                    MediaType.APPLICATION_JSON_VALUE
//                            },
//                            method = RequestMethod.POST,
//                            beanClass = TecnologiaHandlerImpl.class,
//                            operation = @Operation(
//                                    operationId = "createTecnologia",
//                                    responses = {
//                                            @ApiResponse(
//                                                    responseCode = "200",
//                                                    description = "Create a tecnologia",
//                                                    content = @Content(schema = @Schema(
//                                                            implementation = Tecnologia.class
//                                                    ))
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "400",
//                                                    description = "Bad request"
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "500",
//                                                    description = "Internal server error"
//                                            )
//                                    },
//                                    requestBody = @RequestBody(
//                                            content = @Content(schema = @Schema(
//                                                    implementation = Tecnologia.class
//                                            ))
//                                    )
//                            )
//                    ),
//                    @RouterOperation(
//                            path = "/tecnologia/{id}",
//                            produces = {
//                                    MediaType.APPLICATION_JSON_VALUE
//                            },
//                            method = RequestMethod.GET,
//                            beanClass = TecnologiaHandlerImpl.class,
//                            operation = @Operation(
//                                    operationId = "getTecnologiaById",
//                                    responses = {
//                                            @ApiResponse(
//                                                    responseCode = "200",
//                                                    description = "Get a tecnologia by id",
//                                                    content = @Content(schema = @Schema(
//                                                            implementation = Tecnologia.class
//                                                    ))
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "404",
//                                                    description = "Tecnologia not found"
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "500",
//                                                    description = "Internal server error"
//                                            )
//                                    },
//                                    parameters = {
//                                            @Parameter(
//                                                    in = ParameterIn.PATH,
//                                                    name = "id"
//                                            )
//                                    }
//                            )
//                    ),
//                    @RouterOperation(
//                            path = "/tecnologia/{id}",
//                            produces = {
//                                    MediaType.APPLICATION_JSON_VALUE
//                            },
//                            method = RequestMethod.PUT,
//                            beanClass = TecnologiaHandlerImpl.class,
//                            operation = @Operation(
//                                    operationId = "updateTecnologia",
//                                    responses = {
//                                            @ApiResponse(
//                                                    responseCode = "200",
//                                                    description = "Update a tecnologia",
//                                                    content = @Content(schema = @Schema(
//                                                            implementation = Tecnologia.class
//                                                    ))
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "404",
//                                                    description = "Tecnologia not found"
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "500",
//                                                    description = "Internal server error"
//                                            )
//                                    },
//                                    parameters = {
//                                            @Parameter(
//                                                    in = ParameterIn.PATH,
//                                                    name = "id"
//                                            )
//                                    }
//                            )
//                    ),
//                    @RouterOperation(
//                            path = "/tecnologia/{id}",
//                            produces = {
//                                    MediaType.APPLICATION_JSON_VALUE
//                            },
//                            method = RequestMethod.DELETE,
//                            beanClass = TecnologiaHandlerImpl.class,
//                            operation = @Operation(
//                                    operationId = "deleteTecnologia",
//                                    responses = {
//                                            @ApiResponse(
//                                                    responseCode = "200",
//                                                    description = "Delete a tecnologia",
//                                                    content = @Content(schema = @Schema(
//                                                            implementation = Tecnologia.class
//                                                    ))
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "404",
//                                                    description = "Tecnologia not found"
//                                            ),
//                                            @ApiResponse(
//                                                    responseCode = "500",
//                                                    description = "Internal server error"
//                                            )
//                                    },
//                                    parameters = {
//                                            @Parameter(
//                                                    in = ParameterIn.PATH,
//                                                    name = "id"
//                                            )
//                                    }
//                            )
//                    )
//            }
//    )
//    public RouterFunction<ServerResponse> routerFunctionTecnologia2() {
//        return RouterFunctions.route()
//                .GET("/tecnologia", tecnologiaHandler::getAllTecnologias)
//                .POST("/tecnologia", tecnologiaHandler::createTecnologia)
//                .GET("/tecnologia/{id}", tecnologiaHandler::getTecnologiaById)
//                .PUT("/tecnologia/{id}", tecnologiaHandler::updateTecnologia)
//                .DELETE("/tecnologia/{id}", tecnologiaHandler::deleteTecnologia)
//                .build();
//    }
}