package com.ressource.ressource.service;

import com.ressource.ressource.dto.CommentaryDto;
import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.entities.CommentaryEntity;
import com.ressource.ressource.entities.UserEntity;

import java.util.List;

public interface CommentaryService {

    List<CommentaryDto> findAll();

    void addCommentary(CommentaryDto commentaryDto);

    void addReplyToComment(Long id, CommentaryEntity reply);

    CommentaryDto findById(Long id);
}
