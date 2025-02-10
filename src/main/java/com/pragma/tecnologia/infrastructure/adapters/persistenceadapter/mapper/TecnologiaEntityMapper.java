package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.TecnologiaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TecnologiaEntityMapper {
    Tecnologia toModel(TecnologiaEntity entity);
    TecnologiaEntity toEntity(Tecnologia model);
}