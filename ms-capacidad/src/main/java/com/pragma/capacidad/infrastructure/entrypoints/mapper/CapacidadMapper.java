package com.pragma.capacidad.infrastructure.entrypoints.mapper;

import com.pragma.capacidad.domain.model.Capacidad;
import com.pragma.capacidad.infrastructure.entrypoints.dto.CapacidadDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CapacidadMapper {
    Capacidad capacidadDTOToCapacidad(CapacidadDTO capacidadDTO);
    CapacidadDTO capacidadToCapacidadDTO(Capacidad capacidad);
}