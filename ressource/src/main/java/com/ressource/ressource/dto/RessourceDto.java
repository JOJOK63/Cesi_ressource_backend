package com.ressource.ressource.dto;

import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.enumeration.StatusPublication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RessourceDto{

    private Long id;

    private String title;

    private String description;

    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime eventStart;

    private LocalDateTime eventEnd;

    private String file;

    private CategoryDto category;

    private UserDto utilisateur;

    private StatusPublication etat = StatusPublication.WAIT;



}
