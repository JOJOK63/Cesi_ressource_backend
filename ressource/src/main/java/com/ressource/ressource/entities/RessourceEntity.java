package com.ressource.ressource.entities;


import com.ressource.ressource.enumeration.StatusPublication;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "ressources")
public class RessourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "event_start")
    private LocalDateTime eventStart;

    @Column(name = "event_end")
    private LocalDateTime eventEnd;

    @Column(name = "file")
    private String file;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity utilisateur;

    @Enumerated(EnumType.STRING)
    private StatusPublication etat;

    @OneToMany(mappedBy = "ressource", cascade = CascadeType.ALL)
    private List<CommentaryEntity> commentaires;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity category;


}

