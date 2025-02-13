package com.pragma.bootcamp.infrastructure.entrypoints.mapper;

import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.infrastructure.entrypoints.dto.BootcampDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BootcampMapper {
    Bootcamp bootcampDTOToBootcamp(BootcampDTO bootcampDTO);
    BootcampDTO bootcampToBootcampDTO(Bootcamp bootcamp);
}