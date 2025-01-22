package com.ressource.ressource.mapper.implementation;

import com.ressource.ressource.dto.CommentaryDto;
import com.ressource.ressource.entities.CommentaryEntity;
import com.ressource.ressource.mapper.CommentaryMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CommentaryMapperImpl implements CommentaryMapper {

    @Autowired
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CommentaryDto convertToDto(CommentaryEntity commentaryEntity){
        return this.modelMapper.map(commentaryEntity, CommentaryDto.class);
    }

    @Override
    public CommentaryEntity convertToEntity(CommentaryDto commentaryDto) {
        return this.modelMapper.map(commentaryDto, CommentaryEntity.class);
    }

    @Override
    public List<CommentaryDto> convertToDtoList(List<CommentaryEntity> commentaryEntities) {
        return commentaryEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
