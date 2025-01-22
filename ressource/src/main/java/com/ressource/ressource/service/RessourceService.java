package com.ressource.ressource.service;

import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.entities.RessourceEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface RessourceService {
    /**
     * Renvoit toutes les ressources
     * @return List<RessourceDto>
     */
    List<RessourceDto> findAll();

    /**
     * Renvoit une ressource à partir de l'Id
     * @param id Id de la ressource
     * @return La ressource à partir de son Id
     */
    RessourceDto findById(Long id);

    /**
     * Enregistre en base de donnée
     * @param ressourceDto La ressource à enregistrer
     */
    void save(RessourceDto ressourceDto);

    /**
     * Supprime une ressource à partir de son Id
     * @param id L'Id de la ressource
     */
    void deletedById(Long id);

    @Query("SELECT ressource FROM RessourceEntity ressource WHERE ressource.utilisateur.id = :id")
    List<RessourceDto> findByUserId(Long id);

    void deleteRessourcesById(Long id, HttpSession httpSession);

    ResponseEntity<String> ajouterRessources(@RequestBody RessourceEntity ressourceEntity, HttpSession httpSession);

}
