package com.ressource.ressource.mapper.implementation;

import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implémentation de UserMapper
 */
@Component
public class UserMapperImpl implements UserMapper {

    /**
     * Mapper
     */
    @Autowired
    private final ModelMapper modelMapper = new ModelMapper();


    /**
     * Convertit un UserEntity en UserDto
     * @param userEntity L'entity à convertir
     * @return Un UserDto
     */
    @Override
    public UserDto convertToDto(UserEntity userEntity) {
        return this.modelMapper.map(userEntity,UserDto.class);
    }

    /**
     * Convertit un UserDto en UserEntity
     * @param userDto Le Dto à convertir
     * @return Un UserEntity
     */
    @Override
    public UserEntity convertToEntity(UserDto userDto) {
        return this.modelMapper.map(userDto,UserEntity.class);
    }
}
