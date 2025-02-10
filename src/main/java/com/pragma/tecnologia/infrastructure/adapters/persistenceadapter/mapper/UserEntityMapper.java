package com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.pragma.tecnologia.domain.model.User;
import com.pragma.tecnologia.infrastructure.adapters.persistenceadapter.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    User toModel(UserEntity entity);
    UserEntity toEntity(User user);
}
