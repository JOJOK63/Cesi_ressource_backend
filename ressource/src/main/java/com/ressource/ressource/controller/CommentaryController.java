package com.ressource.ressource.controller;

import com.ressource.ressource.dto.CommentaryDto;
import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.RessourceEntity;
import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.repositories.CommentaryRepository;
import com.ressource.ressource.response.CommentaryResponse;
import com.ressource.ressource.response.RessourceResponse;
import com.ressource.ressource.response.UsersResponse;
import com.ressource.ressource.service.CommentaryService;
import com.ressource.ressource.service.RessourceService;
import com.ressource.ressource.service.UserService;
import com.ressource.ressource.utils.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentaryController {

    @Autowired
    private CommentaryService commentaryService;

    @Autowired
    private UserService userService;



    @Autowired
    private RessourceService ressourceService;


    @GetMapping("/commentary")
    public ResponseEntity<List<CommentaryDto>> getAllCommentaries() {
        List<CommentaryDto> commentaries = commentaryService.findAll();
        return new ResponseEntity<>(commentaries, HttpStatus.OK);
    }


    @PostMapping("/commentary/{id}")
    public CommentaryResponse addCommentary(@PathVariable Long id, @RequestBody CommentaryDto commentaryDto) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter un commentaire";
        RessourceDto ressource = null;
        UserDto user = null;
        try {
            if (SecurityContext.isConnected()){
                // lier à un utilisateur
                user = userService.findById(SecurityContext.getIdUser());
                if (user != null) {
                    // lier à une ressource
                    ressource = ressourceService.findById(id);
                    if (ressource != null) {
                        commentaryDto.setUtilisateur(user);
                        commentaryDto.setRessource(ressource);
                        commentaryService.addCommentary(commentaryDto);
                        status = HttpStatus.ACCEPTED;
                        message ="Commentaire ajouté avec succès !";
                    }


                }
            }
        } catch (Exception e) {
            // Gérez les exceptions possibles
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Aucun utilisateur connecté. Veuillez vous connecter pour posterun commentaire à une ressource.";
        }
        return new CommentaryResponse(status,message, ressource, user);
    }

    @PostMapping("/commentary/{idRessource}/{idComment}")
    public CommentaryResponse addReplyToComment(@PathVariable Long idRessource, @PathVariable Long idComment, @RequestBody CommentaryDto commentaryDto){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter un commentaire";

        try {
            if (SecurityContext.isConnected()){
                // lier à un utilisateur
                UserDto user = userService.findById(SecurityContext.getIdUser());
                if (user != null) {
                    // lier à une ressource
                    RessourceDto ressource = ressourceService.findById(idRessource);
                    if (ressource != null) {
                        // lier à un commentaire
                        CommentaryDto commentaire = commentaryService.findById(idComment);
                        if (commentaire != null)
                            commentaryDto.setUtilisateur(user);
                            commentaryDto.setRessource(ressource);
                            commentaryDto.setParentComment(commentaire);
                            commentaryService.addCommentary(commentaryDto);
                            status = HttpStatus.ACCEPTED;
                            message ="Commentaire ajouté avec succès !";
                    }
                }
            }
        } catch (Exception e) {
            // Gérez les exceptions possibles
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Aucun utilisateur connecté. Veuillez vous connecter pour posterun commentaire à une ressource.";
        }
        return new CommentaryResponse(status,message,null, null);

    }
}
