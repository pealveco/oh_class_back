package com.pragma.bootcamp.domain.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record Bootcamp(
    @NotNull(message = "ID cannot be null.") Long id,
    @NotBlank(message = "Name cannot be null or blank.") String name,
    @NotBlank(message = "Description cannot be null or blank.") String description,
    @NotNull(message = "Capacidades cannot be null.")
    @Size(min = 1, max = 4, message = "A bootcamp must have between 1 and 4 capacities.") List<Long> capacidadIds
) {}