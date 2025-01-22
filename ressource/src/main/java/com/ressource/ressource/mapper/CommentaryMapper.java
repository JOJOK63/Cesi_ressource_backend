package com.ressource.ressource.mapper;

import com.ressource.ressource.dto.CommentaryDto;
import com.ressource.ressource.entities.CommentaryEntity;

import java.util.List;

public interface CommentaryMapper {
    CommentaryDto convertToDto(CommentaryEntity commentaryEntity);

    CommentaryEntity convertToEntity(CommentaryDto commentaryDto);

    List<CommentaryDto> convertToDtoList(List<CommentaryEntity> commentaryEntities);
}
