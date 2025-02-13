package com.pragma.capacidad.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CapacidadDTO(
        Long id,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String name,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(max = 90, message = "La descripción no puede superar los 90 caracteres")
        String description,

        @Size(min = 3, max = 20, message = "Debe tener entre 3 y 20 tecnologías")
        Set<Long> tecnologiaIds
) {}