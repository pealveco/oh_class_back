package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CapacidadMapper {
    Capacidad capacidadDTOToCapacidad(CapacidadDTO capacidadDTO);
    CapacidadDTO capacidadToCapacidadDTO(Capacidad capacidad);
}