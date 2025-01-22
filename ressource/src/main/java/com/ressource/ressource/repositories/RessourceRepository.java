package com.ressource.ressource.repositories;

import com.ressource.ressource.entities.RessourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la table ressource
 */
@Repository
public interface RessourceRepository extends JpaRepository<RessourceEntity,Long> {

}
