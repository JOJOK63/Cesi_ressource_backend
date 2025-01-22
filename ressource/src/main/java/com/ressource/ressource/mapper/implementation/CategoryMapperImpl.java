package com.ressource.ressource.mapper.implementation;

import com.ressource.ressource.dto.CategoryDto;
import com.ressource.ressource.entities.CategoryEntity;
import com.ressource.ressource.mapper.CategoryMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Autowired
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CategoryDto convertToDto(CategoryEntity categoryEntity){
        return this.modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryEntity convertToEntity(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto, CategoryEntity.class);
    }

    @Override
    public List<CategoryDto> convertToDtoList(List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
