package com.ressource.ressource.service.Impl;

import com.ressource.ressource.dto.CommentaryDto;
import com.ressource.ressource.entities.CommentaryEntity;
import com.ressource.ressource.mapper.CommentaryMapper;
import com.ressource.ressource.repositories.CommentaryRepository;
import com.ressource.ressource.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentaryServiceImpl implements CommentaryService {

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Autowired
    private CommentaryMapper commentaryMapper;


    @Override
    public List<CommentaryDto> findAll() {
        List<CommentaryEntity> commentaryEntityList = commentaryRepository.findAll();
        return commentaryEntityList.stream()
                .map(commentaryMapper::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public void addCommentary(CommentaryDto commentaryDto) {
        CommentaryEntity commentaryEntity = commentaryMapper.convertToEntity(commentaryDto);
        commentaryRepository.save(commentaryEntity);
    }

    @Override
    public void addReplyToComment(Long id, CommentaryEntity reply){
        CommentaryEntity parentComment = commentaryRepository.findById(id).orElse(null);
        commentaryRepository.save(parentComment);
    }

    @Override
    public CommentaryDto findById(Long id){
        CommentaryEntity commentaryEntity = commentaryRepository.findById(id).orElse(null);
        return commentaryMapper.convertToDto(commentaryEntity);
    }


}
