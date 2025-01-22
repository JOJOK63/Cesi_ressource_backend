package com.ressource.ressource.dto;


import com.ressource.ressource.entities.CommentaryEntity;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentaryDto {

    private Long id;

    private UserDto utilisateur;

    private RessourceDto ressource;

    private LocalDate date;

    private String contenu;

    private CommentaryDto parentComment;
}
