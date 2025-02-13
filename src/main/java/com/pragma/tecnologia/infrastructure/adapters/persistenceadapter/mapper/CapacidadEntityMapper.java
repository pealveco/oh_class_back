package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.CapacidadEntity;

@Mapper(componentModel = "spring")
public interface CapacidadEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "tecnologiaIds", target = "tecnologiaIds")
    Capacidad toModel(CapacidadEntity entity);
    CapacidadEntity toEntity(Capacidad model);
}