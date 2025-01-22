package com.ressource.ressource.service;

import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.entities.ValidationEntity;

public interface ValidationService {

    void enregistrer(UserEntity user);

    ValidationEntity lireEnFonctionDuCode(String code);
}
