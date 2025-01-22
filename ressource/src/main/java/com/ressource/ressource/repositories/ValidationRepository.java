package com.ressource.ressource.repositories;

import com.ressource.ressource.entities.ValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ValidationRepository extends JpaRepository<ValidationEntity, Integer> {

    ValidationEntity findByCode(String code);
}