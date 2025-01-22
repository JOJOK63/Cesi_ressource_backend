package com.ressource.ressource.controller;


import com.ressource.ressource.dto.CategoryDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.CategoryEntity;
import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.response.CategoryResponse;
import com.ressource.ressource.response.RessourceResponse;
import com.ressource.ressource.service.CategoryService;
import com.ressource.ressource.service.UserService;
import com.ressource.ressource.utils.SecurityContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    // Lister les catégories
    @GetMapping("/categories")
    public CategoryResponse findAll(){
        return new CategoryResponse(HttpStatus.ACCEPTED, "ok", categoryService.findAll());
    }

    // Ajouter une Catégorie
    @PostMapping("/categories")
    public CategoryResponse addCategorie(@RequestBody CategoryDto categoryDto) {

        HttpStatus status = HttpStatus.ACCEPTED;
        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
        try {
            // Vérifier si un utilisateur est connecté
            if (SecurityContext.isConnected()) {
                UserDto user = userService.findById(SecurityContext.getIdUser());
                if (user != null) {
                    // On récupère le role de l'utilisateur
                    Role userRole = user.getRole();
                    // Si le role est défférent de "Citoyen"
                    if (userRole != Role.CITOYEN) {
                        categoryDto.getCategory_name();
                        categoryService.addCategory(categoryDto);
                        status = HttpStatus.ACCEPTED;
                        message = "Catéorie ajouté avec succès !!";
                    } else {
                        // Pour les citoyens
                        status = HttpStatus.NOT_FOUND;
                        message = "Les citoyens ne peuvent pas ajouter de catégorie";
                    }
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Aucun utilisateur connecté. Veuillez vous connecter pour mettre à jour une ressource.";
        }
        return new CategoryResponse(status,message,null);
    }


    // Modifier une Catégorie
    @PutMapping("/categories/{id}")
    public CategoryResponse editCategorie(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {

        HttpStatus status = HttpStatus.ACCEPTED;
        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";

        try {
            // Vérifier si un utilisateur est connecté
            if (SecurityContext.isConnected()) {
                UserDto user = userService.findById(SecurityContext.getIdUser());
                CategoryDto existingCategory = categoryService.findById(id);
                if (user != null) {
                    // On récupère le role de l'utilisateur
                    Role userRole = user.getRole();
                    // Si le role est défférent de "Citoyen"
                    if (userRole != Role.CITOYEN) {
                        if (existingCategory != null) {
                            existingCategory.setCategory_name(categoryDto.getCategory_name());
                            categoryService.addCategory(existingCategory);
                            status = HttpStatus.ACCEPTED;
                            message = "Catéorie mise à jour avec succès !!";
                        } else {
                            status = HttpStatus.NOT_FOUND;
                            message = "Catéorie introuvable !!";
                        }

                    } else {
                        // Pour les citoyens
                        status = HttpStatus.BAD_REQUEST;
                        message = "Les citoyens ne peuvent pas ajouter de catégorie";
                    }
                } else {
                    status = HttpStatus.UNAUTHORIZED;
                    message = "L'utilisateur n'existe pas";
                }


            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Aucun utilisateur connecté. Veuillez vous connecter pour mettre à jour une ressource.";
        }
        return new CategoryResponse(status,message, null);
    }

    // Supprimer une catégorie
    @DeleteMapping("/categories/{id}")
    public CategoryResponse deleteCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";

        try {
            // Vérifier si un utilisateur est connecté
            if (SecurityContext.isConnected()) {
                UserDto user = userService.findById(SecurityContext.getIdUser());
                CategoryDto existingCategory = categoryService.findById(id);
                if (user != null) {
                    // On récupère le role de l'utilisateur
                    Role userRole = user.getRole();
                    // Si le role est défférent de "Citoyen"
                    if (userRole != Role.CITOYEN) {
                        if (existingCategory != null) {
                            categoryService.deleteCategory(id);
                            status = HttpStatus.ACCEPTED;
                            message = "Catéorie supprimée avec succès !!";
                        } else {
                            status = HttpStatus.NOT_FOUND;
                            message = "Catéorie introuvable !!";
                        }

                    } else {
                        // Pour les citoyens
                        status = HttpStatus.BAD_REQUEST;
                        message = "Les citoyens ne peuvent pas ajouter de catégorie";
                    }
                } else {
                    status = HttpStatus.UNAUTHORIZED;
                    message = "L'utilisateur n'existe pas";
                }
            }
        }catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
            log.error(e.getMessage());
        }
        return new CategoryResponse(status,message, null);
    }

}
