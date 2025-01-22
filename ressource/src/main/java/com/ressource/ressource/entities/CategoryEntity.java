package com.ressource.ressource.entities;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category_name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<RessourceEntity> ressources;
}

