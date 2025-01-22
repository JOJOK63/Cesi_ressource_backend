package com.ressource.ressource.service.Impl;

import com.ressource.ressource.dto.CategoryDto;
import com.ressource.ressource.entities.CategoryEntity;
import com.ressource.ressource.mapper.CategoryMapper;
import com.ressource.ressource.repositories.CategoryRepository;
import com.ressource.ressource.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public CategoryMapper categoryMapper;

    // lister les catégories
    @Override
    public List<CategoryDto> findAll(){
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return categoryMapper.convertToDtoList(categoryEntityList);
    }

    // lister une catégorie par Id
    @Override
    public CategoryDto findById(Long id){
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
        return categoryMapper.convertToDto(categoryEntity);
    }

    // Ajouter une catégorie // modifier une catégorie
    @Override
    public void addCategory (CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryMapper.convertToEntity(categoryDto);
        categoryRepository.save(categoryEntity);
    }

    // Supprimer une catégorie
    @Override
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }

}
