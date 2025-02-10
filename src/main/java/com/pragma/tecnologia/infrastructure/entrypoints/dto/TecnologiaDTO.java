package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TecnologiaDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String name,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(max = 90, message = "La descripción no puede superar los 90 caracteres")
        String description
) {}
