package com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.mapper;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.entity.BootcampEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BootcampEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "capacidadIds", target = "capacidadIds")
    Bootcamp toModel(BootcampEntity entity);
    BootcampEntity toEntity(Bootcamp model);
}