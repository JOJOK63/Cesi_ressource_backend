package com.ressource.ressource.mapper.implementation;

import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.entities.RessourceEntity;
import com.ressource.ressource.mapper.RessourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

        @Component
        public class RessourceMapperImpl implements RessourceMapper {
            @Autowired
            private final ModelMapper modelMapper = new ModelMapper();

            @Override
            public RessourceDto convertToDto(RessourceEntity ressourceEntity){
                return this.modelMapper.map(ressourceEntity, RessourceDto.class);
            }
            @Override
            public RessourceEntity convertToEntity(RessourceDto ressourceDto) {
                return this.modelMapper.map(ressourceDto,RessourceEntity.class);
            }
            @Override
            public List<RessourceDto> convertToDtoList(List<RessourceEntity> ressourceEntities) {
                return ressourceEntities.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
            }
        }

