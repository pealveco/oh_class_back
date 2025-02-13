package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TecnologiaMapper {
    Tecnologia tecnologiaDTOToTecnologia(TecnologiaDTO tecnologiaDTO);
    TecnologiaDTO tecnologiaToTecnologiaDTO(Tecnologia tecnologia);
    TecnologiaDTO tecnologiaIdToTecnologiaDTO(Long id);
}