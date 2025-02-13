package com.pragma.capacidad.domain.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public record Capacidad(
    Long id,
    @NotNull @NotEmpty String name,
    @NotNull @NotEmpty String description,
    @NotNull @Size(min = 3, max = 20) Set<@NotNull Long> tecnologiaIds
) {
    public Capacidad {
        if (tecnologiaIds == null) {
            tecnologiaIds = new HashSet<>();
        }
    }
}