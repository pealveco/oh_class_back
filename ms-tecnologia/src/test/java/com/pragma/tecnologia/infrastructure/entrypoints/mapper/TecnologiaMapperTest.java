package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class TecnologiaMapperTest {

    private final TecnologiaMapper mapper = Mappers.getMapper(TecnologiaMapper.class);

    @Test
    void tecnologiaDTOToTecnologia() {
        TecnologiaDTO dto = new TecnologiaDTO(1L, "Java", "Java description");
        Tecnologia entity = mapper.tecnologiaDTOToTecnologia(dto);
        assertNotNull(entity);
        assertEquals(dto.id(), entity.id());
        assertEquals(dto.name(), entity.name());
        assertEquals(dto.description(), entity.description());
    }

    @Test
    void tecnologiaToTecnologiaDTO() {
        Tecnologia entity = new Tecnologia(1L, "Java", "Java description");
        TecnologiaDTO dto = mapper.tecnologiaToTecnologiaDTO(entity);
        assertNotNull(dto);
        assertEquals(entity.id(), dto.id());
        assertEquals(entity.name(), dto.name());
        assertEquals(entity.description(), dto.description());
    }

    @Test
    void tecnologiaIdToTecnologiaDTO() {
        Long id = 1L;
        TecnologiaDTO dto = mapper.tecnologiaIdToTecnologiaDTO(id);
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertNull(dto.name());
        assertNull(dto.description());
    }
}