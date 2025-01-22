package com.ressource.ressource.controller;

import com.ressource.ressource.dto.LoginAdminDto;
import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.RessourceEntity;
import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.response.UserResponse;
import com.ressource.ressource.response.UsersResponse;
import com.ressource.ressource.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.hibernate.sql.results.ResultsLogger_$logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDate;
import java.util.List;

/**
 * Controller users
 */

@Slf4j
@RestController
public class UserController {

    /**
     * Service user
     */
    @Autowired
    private UserService userService;

    /**
     * Envoit la liste de tous les utilisateurs
     * @return Liste d'utilisateur
     */
    @GetMapping("/users")
    public UsersResponse findAll(){
        return new UsersResponse(HttpStatus.ACCEPTED,"ok",userService.findAll());
    }

    /**
     * Récupère un utilisateur à partir de son id.
     * @param id Id de l'utilisateur
     * @return Un utilisateur
     */
    @GetMapping("/users/{id}")
    public UserResponse findById(@PathVariable Long id){
        return new UserResponse(HttpStatus.ACCEPTED,"ok",userService.findById(id));
    }

    /**
     * Ajoute un utilisateur
     * @param userDto L'utilisateur à ajouter
     */
    @PostMapping("/users")
    public UserResponse addUser(@RequestBody UserDto userDto){
        return new UserResponse(HttpStatus.ACCEPTED,"ok",userService.save(userDto));
    }



    /**
     * Modifie un utilisateur
     * @param userDto L'utilisateur à ajouter
     * @return Un utilisateur
     */
    @PutMapping("/users")
    public void updateUser(@RequestBody UserDto userDto){
        userService.save(userDto);
    }

    /**
     * Supprime un utilisateur à partir de son id
     * @param id Id de l'utilisateur
     */
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }





    // ------------------ ADMIN -------------------

    @DeleteMapping("/admin/users/{id}")
    public void deleteAdmin(@PathVariable Long id, HttpSession httpSession){
        userService.deleteAdminById(id, httpSession);
    }


    /*// Désactivation d'un compte
    @PutMapping("/admin/users/ban/{id}")
    public void desactivationCompteByAdmin(@PathVariable Long id, HttpSession httpSession){
        userService.desactiverCompte(id, httpSession);
    }*/


}
