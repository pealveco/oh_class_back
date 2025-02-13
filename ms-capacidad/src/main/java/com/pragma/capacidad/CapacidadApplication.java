package com.pragma.capacidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pragma.capacidad", "com.pragma.tecnologia"})
public class CapacidadApplication {

    public static void main(String[] args) {
        SpringApplication.run(CapacidadApplication.class, args);
    }
}