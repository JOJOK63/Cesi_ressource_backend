package com.ressource.ressource.repositories;

import com.ressource.ressource.entities.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {
}
