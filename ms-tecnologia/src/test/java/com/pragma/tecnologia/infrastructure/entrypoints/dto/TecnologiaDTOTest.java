package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TecnologiaDTOTest {

    @Test
    void id() {
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Lenguaje de programación");
        assertEquals(1L, dto.id());
    }

    @Test
    void name() {
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Lenguaje de programación");
        assertEquals("Java", dto.name());
    }

    @Test
    void description() {
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Lenguaje de programación");
        assertEquals("Lenguaje de programación", dto.description());
    }
}