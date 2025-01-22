package com.ressource.ressource.mapper;

import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.UserEntity;

/**
 * Mapper pour User
 */
public interface UserMapper {

    /**
     * Convertit un UserEntity en UserDto
     * @param userEntity L'entity à convertir
     * @return Un UserDto
     */
    UserDto convertToDto(UserEntity userEntity);

    /**
     * Convertit un UserDto en UserEntity
     * @param userDto Le Dto à convertir
     * @return Un UserEntity
     */
    UserEntity convertToEntity(UserDto userDto);
}
