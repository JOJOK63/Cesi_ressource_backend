package com.ressource.ressource.controller;

import com.ressource.ressource.dto.CategoryDto;
import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.enumeration.StatusPublication;
import com.ressource.ressource.response.RessourceResponse;
import com.ressource.ressource.response.RessourcesResponse;
import com.ressource.ressource.service.RessourceService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RessourceControllerTest {
    @Mock
    private RessourceService ressourceService ;
    @InjectMocks
    private final RessourceController ressourceController = new RessourceController();

    @BeforeAll
    static void init(){

    }
    @Test
    void getAllTest() {
        final UserDto utilisateur = new UserDto();
        utilisateur.setId(1L);

        final CategoryDto category = new CategoryDto();
        category.setId(1L);
        List<RessourceDto> expected = new ArrayList<>();
        RessourceDto ressourceDto1 = new RessourceDto(1L,
                "Titre de la ressource",
                "Description de la ressource",
                LocalDateTime.now(),
                LocalDateTime.of(2024, 4, 24, 10, 0),
                LocalDateTime.of(2024, 4, 25, 17, 0),
                "fichier.png",
                category,
                utilisateur,
                StatusPublication.WAIT
        );

        expected.add(ressourceDto1);
        when(this.ressourceService.findAll()).thenReturn(expected);

        RessourcesResponse response = this.ressourceController.getAll();
        List<RessourceDto> actual = response.getResources();

        assertEquals(expected, actual);
    }


    @Test
    void getOneTest(){
        final UserDto utilisateur = new UserDto();
        utilisateur.setId(1L);

        final CategoryDto category = new CategoryDto();
        category.setId(1L);
        RessourceDto expected = new RessourceDto(1L,
                "Titre de la ressource",
                "Description de la ressource",
                LocalDateTime.now(),
                LocalDateTime.of(2024, 4, 24, 10, 0),
                LocalDateTime.of(2024, 4, 25, 17, 0),
                "fichier.png",
                category,
                utilisateur,
                StatusPublication.WAIT
        );

        when(this.ressourceService.findById(1L)).thenReturn(expected);

        RessourceResponse response = this.ressourceController.getOne(1L);
        RessourceDto actual = response.getResource();

        assertEquals(expected, actual);
    }

}