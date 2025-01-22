package com.ressource.ressource.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "commentary")
public class CommentaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_ressource")
    private RessourceEntity ressource;


    private String contenu;

    private LocalDate date = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private CommentaryEntity parentComment;

}
