package com.pragma.capacidad.infrastructure.adapters.persistenceadapter.mapper;

import com.pragma.capacidad.domain.model.Capacidad;
import com.pragma.capacidad.infrastructure.adapters.persistenceadapter.entity.CapacidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CapacidadEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "tecnologiaIds", target = "tecnologiaIds")
    Capacidad toModel(CapacidadEntity entity);
    CapacidadEntity toEntity(Capacidad model);
}