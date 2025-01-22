package com.ressource.ressource.mapper;

import com.ressource.ressource.dto.CategoryDto;
import com.ressource.ressource.entities.CategoryEntity;

import java.util.List;

public interface CategoryMapper {
    CategoryDto convertToDto(CategoryEntity categoryEntity);

    CategoryEntity convertToEntity(CategoryDto categoryDto);

    List<CategoryDto> convertToDtoList(List<CategoryEntity> categoryEntities);
}
