package com.pragma.bootcamp.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record BootcampDTO(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String name,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(max = 90, message = "La descripción no puede superar los 90 caracteres")
        String description,

        @Size(min = 1, max = 4, message = "Debe tener entre 1 y 4 capacidadIds")
        Set<Long> capacidadIds
) {}