package com.pragma.tecnologia;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Tecnologia API",
        version = "1.0",
        description = "Documentación de la API de Tecnologia"
))
public class TecnologiaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TecnologiaApplication.class, args);
    }
}