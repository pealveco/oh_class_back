package com.pragma.capacidad.domain.model;

import java.util.HashSet;
import java.util.Set;

public record Capacidad(Long id, String name, String description, Set<Long> tecnologiaIds) {
    public Capacidad {
        if (tecnologiaIds == null) {
            tecnologiaIds = new HashSet<>();
        }
    }
}