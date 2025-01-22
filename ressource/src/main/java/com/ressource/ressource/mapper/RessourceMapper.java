package com.ressource.ressource.mapper;

import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.entities.RessourceEntity;

import java.util.List;

public interface RessourceMapper {
    RessourceDto convertToDto(RessourceEntity ressourceEntity);

    RessourceEntity convertToEntity(RessourceDto ressourceDto);

    List<RessourceDto> convertToDtoList(List<RessourceEntity> ressourceEntities);
}
