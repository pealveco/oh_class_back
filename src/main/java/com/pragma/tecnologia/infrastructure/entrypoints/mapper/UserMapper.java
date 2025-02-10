package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.User;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}