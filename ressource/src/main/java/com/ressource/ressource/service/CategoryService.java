package com.ressource.ressource.service;

import com.ressource.ressource.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    //lister les catégories
    List<CategoryDto> findAll();

    // lister une catégorie par Id
    CategoryDto findById(Long id);

    // Ajouter une catégorie
    void addCategory(CategoryDto categoryDto);

    // Supprimer une catégorie
    void deleteCategory(Long id);
}
