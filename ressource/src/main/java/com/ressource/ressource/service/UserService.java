package com.ressource.ressource.service;

import com.ressource.ressource.dto.LoginAdminDto;

import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.response.UserResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Service User
 */
public interface UserService{



    /**
     * Récupère tous les utilisateurs
     * @return Une liste d'utilisateur
     */
    List<UserDto> findAll();


    /**
     * Récupère un utilisateur à partir de son id.
     * @param id Id de l'utilisateur
     * @return Un utilisateur
     */
    UserDto findById(Long id);


    UserDto findByEmail(String email);

    /**
     * Ajoute un utilisateur
     * @param userDto L'utilisateur à ajouter
     * @return Un utilisateur
     */
    UserDto save(UserDto userDto);

    /**
     * Supprime un utilisateur à partir de son id
     * @param id Id de l'utilisateur
     */
    void deleteById(Long id);


    //void inscription(UserEntity userEntity);


    ResponseEntity<String> deleteAdminById(Long id, HttpSession httpSession);

    void inscription(UserDto userDto);

    UserDto loginUser(UserDto userDto);

    UserResponse loginAdmin(LoginAdminDto loginAdminDto, HttpSession httpSession);


    ResponseEntity<?> desactiverCompte(Long id, HttpSession httpSession);

    void activation(Map<String, String> activation);
}
