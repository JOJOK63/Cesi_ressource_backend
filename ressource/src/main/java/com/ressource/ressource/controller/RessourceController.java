package com.ressource.ressource.controller;

import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.RessourceEntity;
import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.enumeration.StatusPublication;
import com.ressource.ressource.response.RessourceResponse;
import com.ressource.ressource.response.RessourcesResponse;
import com.ressource.ressource.service.RessourceService;
import com.ressource.ressource.service.UserService;
import com.ressource.ressource.utils.SecurityContext;
import com.ressource.ressource.utils.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
     * Controller Ressources
     */
@Log4j2
@RestController
@RequestMapping("/ressources")
public class RessourceController {


    @Autowired
    private RessourceService ressourceService;

    @Autowired
    private UserService userService;

     @GetMapping
    public RessourcesResponse getAll(){
        return new RessourcesResponse(HttpStatus.ACCEPTED,"ok",ressourceService.findAll());
    }
    @GetMapping("/{id}")
    public RessourceResponse getOne(@PathVariable Long id){
        return new RessourceResponse(HttpStatus.ACCEPTED,"ok",ressourceService.findById(id));
    }
    @PostMapping
    public RessourceResponse add(@RequestBody RessourceDto ressourceDto){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
        System.out.println(RequestContextHolder.getRequestAttributes());
        try {
            UserDto user = userService.findById(SecurityContext.getIdUser());
            if (user != null) {
                // Créer la ressource associé à l'utilisateur connecté
                ressourceDto.setUtilisateur(user);
                ressourceService.save(ressourceDto);
                status = HttpStatus.ACCEPTED;
                message ="Ressource ajouté avec succès !";
            }

        } catch (Exception e) {
            // Gérez les exceptions possibles
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Aucun utilisateur connecté. Veuillez vous connecter pour mettre à jour une ressource.";
        }
        return new RessourceResponse(status,message,ressourceDto);
    }
    @PutMapping("/{id}")
    public RessourceResponse updateRessource(@PathVariable Long id, @RequestBody RessourceDto ressourceDto) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
        RessourceDto existingRessource = null;
        try {
            UserDto user = userService.findById(SecurityContext.getIdUser());
            if (user != null) {
                // Vérifiez si la ressource à mettre à jour appartient à l'utilisateur connecté
                existingRessource = ressourceService.findById(id);
                if (existingRessource != null)
                    if (existingRessource.getUtilisateur().getId().equals(user.getId()) || user.getRole()!= Role.CITOYEN) {
                    existingRessource = ressourceDto;

                    // Enregistrer la ressource mise à jour
                    ressourceService.save(existingRessource);
                    status = HttpStatus.ACCEPTED;
                    message = "Ressource mise à jour avec succès !";
                }
            }

        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Une erreur est survenue lors de la mise à jour de la ressource.";
            log.error(e.getMessage());
        }

        return new RessourceResponse(status,message,existingRessource);
    }

    @DeleteMapping("/{id}")
    public RessourceResponse deleteRessource(@PathVariable Long id) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Vous ne pouvez pas supprimer cette ressource, elle ne vous appartient pas";
        RessourceDto existingRessource = null;
        try {
            if (SecurityContext.isConnected()) {
                UserDto user = userService.findById(SecurityContext.getIdUser());
                if (user != null) {
                     existingRessource = ressourceService.findById(id);
                    if (existingRessource != null && existingRessource.getUtilisateur().getId().equals(user.getId())) {
                        // supprimer la ressource
                        ressourceService.deletedById(id);
                        status = HttpStatus.ACCEPTED;
                        message = "Ressource supprimé avec succès !";
                    }
                }
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
            log.error(e.getMessage());
        }
        return new RessourceResponse(status,message,existingRessource);
    }



///**
// * Validation de la ressource
// * @param id Id de la ressource
// * @return RessourceResponse
// */
//@PutMapping("/{id}")
//public RessourceResponse validateRessource(@PathVariable Long id){
//    RessourceResponse ressourceResponse = new RessourceResponse(HttpStatus.UNAUTHORIZED,"Vous n'êtes pas autorisé à accéder sur cette page",null);
//
//    if (Session.isConnected() && Session.getRole() != Role.CITOYEN){
//        RessourceDto ressource = ressourceService.findById(id);
//
//        if (ressource != null){
//            ressource.setEtat(StatusPublication.ACCEPTED);
//            ressourceService.save(ressource);
//            ressourceResponse = new RessourceResponse(HttpStatus.ACCEPTED,"La ressource a bien été validée",ressource);
//        }else {
//            ressourceResponse = new RessourceResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur c'est produit",null);
//        }
//    }
//    return ressourceResponse ;
//}

//        // ------------------- ADMIN ----------------
//
//    @DeleteMapping("admin/ressources/{id}")
//    public void deleteRessources(@PathVariable Long id, HttpSession httpSession){
//        ressourceService.deleteRessourcesById(id, httpSession);
//    }
        // ------------------- ADMIN ----------------

    /*@DeleteMapping("admin/ressources/{id}")
    public void deleteRessources(@PathVariable Long id, HttpSession httpSession){
        ressourceService.deleteRessourcesById(id, httpSession);
    }


    @PostMapping("admin/ressources")
    public void ajouterRessources(@RequestBody RessourceEntity ressourceEntity, HttpSession httpSession) {
        ressourceService.ajouterRessources(ressourceEntity, httpSession);
    }




    @PutMapping("admin/ressources/{id}")
    public ResponseEntity<?> editerRessources(@PathVariable Long id, @RequestBody RessourceDto ressourceDto, HttpSession httpSession) {
        // Vérifier qu'un admin est connecté
        UserEntity admin = (UserEntity) httpSession.getAttribute("id_admin");
        if (admin != null) {
            // Si l'utilisateur est un admin
            // on cherche si la ressource existe
            RessourceDto existingRessource = ressourceService.findById(id);
            if (existingRessource != null) {
                // Mettre à jour les champs de la ressource avec les nouvelles valeurs
                existingRessource.setTitle(ressourceDto.getTitle());
                existingRessource.setDescription(ressourceDto.getDescription());
                // Continuez avec les autres champs...

                // Enregistrer la ressource mise à jour
                ressourceService.save(existingRessource);
                return ResponseEntity.ok("Ressource mise à jour avec succès");
            }

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aucun admin connecté. Veuillez vous connecter pour éditer une ressource.");
    }*/





    }
//
//
//    package com.ressource.ressource.controller;
//
//import com.ressource.ressource.dto.RessourceDto;
//import com.ressource.ressource.dto.UserDto;
//import com.ressource.ressource.entities.RessourceEntity;
//import com.ressource.ressource.entities.UserEntity;
//import com.ressource.ressource.enumeration.Role;
//import com.ressource.ressource.enumeration.StatusPublication;
//import com.ressource.ressource.response.RessourceResponse;
//import com.ressource.ressource.response.RessourcesResponse;
//import com.ressource.ressource.service.RessourceService;
//import com.ressource.ressource.service.UserService;
//import com.ressource.ressource.utils.Session;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Controller Ressources
// */
//@Log4j2
//@RestController
//public class RessourceController {
//
//    /**
//     * Service ressouces
//     */
//    @Autowired
//    private RessourceService ressourceService;
//
//    /**
//     * Service Utilisateur
//     */
//    @Autowired
//    private UserService userService;
//
//    /**
//     * Renvoie toutes les ressources
//     * @return Une liste de ressources
//     */
//    @GetMapping("/ressources")
//    public RessourcesResponse findAll(){
//        return new RessourcesResponse(HttpStatus.ACCEPTED,"ok",ressourceService.findAll());
//    }
//
//    /**
//     * Renvoie une ressource
//     * @param id L'id de la ressource renvoyé
//     * @return Une ressource
//     */
//    @GetMapping("/ressources/{id}")
//    public RessourceResponse findById(@PathVariable Long id){
//        return new RessourceResponse(HttpStatus.ACCEPTED,"ok",ressourceService.findById(id));
//    }
//
//    // Poster une ressource
//    //POST : http://localhost:8080/ressources
//    @PostMapping("/post")
//    public RessourceResponse addRessource(@RequestBody RessourceDto ressourceDto){
//        HttpStatus status = HttpStatus.UNAUTHORIZED;
//        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
//
//        try {
//            // Vérifier si un utilisateur est connecté
//            if (Session.isConnected()){
//                UserDto user = userService.findById(Session.getIdUser());
//                if (user != null) {
//                    // Créer la ressource associé à l'utilisateur connecté
//                    ressourceDto.setUtilisateur(user);
//                    ressourceService.save(ressourceDto);
//                    status = HttpStatus.ACCEPTED;
//                    message ="Ressource ajouté avec succès !";
//                }
//            }
//        } catch (Exception e) {
//            // Gérez les exceptions possibles
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            message = "Aucun utilisateur connecté. Veuillez vous connecter pour mettre à jour une ressource.";
//        }
//        return new RessourceResponse(status,message,ressourceDto);
//    }
//
//    // Modifier la ressource
//    @PutMapping("/ressources/{id}")
//    public ResponseEntity<String> updateRessource(@PathVariable Long id, @RequestBody RessourceDto ressourceDto) {
//
//        HttpStatus status = HttpStatus.UNAUTHORIZED;
//        String message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
//        try {
//            if (Session.isConnected()) {
//                // Vérifiez si un utilisateur est connecté
//                UserDto user = userService.findById(Session.getIdUser());
//                if (user != null) {
//                    // Vérifiez si la ressource à mettre à jour appartient à l'utilisateur connecté
//                    RessourceDto existingRessource = ressourceService.findById(id);
//                    if (existingRessource != null && existingRessource.getUtilisateur().getId().equals(user.getId())) {
//                        // Mettre à jour les champs de la ressource avec les nouvelles valeurs
//                        existingRessource.setTitle(ressourceDto.getTitle());
//                        existingRessource.setDescription(ressourceDto.getDescription());
//                        // Enregistrer la ressource mise à jour
//                        ressourceService.save(existingRessource);
//                        status = HttpStatus.ACCEPTED;
//                        message = "Ressource mise à jour avec succès !";
//                    }
//                }
//            } else {
//                // Aucun utilisateur n'est connecté
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aucun utilisateur connecté. Veuillez vous connecter pour mettre à jour une ressource.");
//            }
//        } catch (Exception e) {
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            message = "Une erreur est survenue lors de la mise à jour de la ressource.";
//            log.error(e.getMessage());
//        }
//
//        return ResponseEntity.status(status).body(message);
//    }
//    // Supprimer la ressource
//    // DELETE : http://localhost:8080/ressources/{id}
//    @DeleteMapping("/ressources/{id}")
//    public ResponseEntity<String> deleteRessource(@PathVariable Long id) {
//
//        HttpStatus status = HttpStatus.UNAUTHORIZED;
//        String message = "Vous ne pouvez pas supprimer cette ressource, elle ne vous appartient pas";
//        try {
//            if (Session.isConnected()) {
//                UserDto user = userService.findById(Session.getIdUser());
//                if (user != null) {
//                    RessourceDto existingRessource = ressourceService.findById(id);
//                    if (existingRessource != null && existingRessource.getUtilisateur().getId().equals(user.getId())) {
//                        // supprimer la ressource
//                        ressourceService.deletedById(id);
//                        status = HttpStatus.ACCEPTED;
//                        message = "Ressource supprimé avec succès !";
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//            message = "Aucun utilisateur connecté. Veuillez vous connecter pour ajouter une ressources";
//            log.error(e.getMessage());
//        }
//        return ResponseEntity.status(status).body(message);
//    }
//
//    /**
//     * Validation de la ressource
//     * @param id Id de la ressource
//     * @return RessourceResponse
//     */
//    @GetMapping("/ressources/{id}/validate")
//    public RessourceResponse validateRessource(@PathVariable Long id){
//        RessourceResponse ressourceResponse = new RessourceResponse(HttpStatus.UNAUTHORIZED,"Vous n'êtes pas autorisé à accéder sur cette page",null);
//
//        if (Session.isConnected() && Session.getRole() != Role.CITOYEN){
//            RessourceDto ressource = ressourceService.findById(id);
//
//            if (ressource != null){
//                ressource.setEtat(StatusPublication.ACCEPTED);
//                ressourceService.save(ressource);
//                ressourceResponse = new RessourceResponse(HttpStatus.ACCEPTED,"La ressource a bien été validée",ressource);
//            }else {
//                ressourceResponse = new RessourceResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Une erreur c'est produit",null);
//            }
//        }
//        return ressourceResponse ;
//    }
//
////        // ------------------- ADMIN ----------------
////
////    @DeleteMapping("admin/ressources/{id}")
////    public void deleteRessources(@PathVariable Long id, HttpSession httpSession){
////        ressourceService.deleteRessourcesById(id, httpSession);
////    }
//    // ------------------- ADMIN ----------------
//
//    /*@DeleteMapping("admin/ressources/{id}")
//    public void deleteRessources(@PathVariable Long id, HttpSession httpSession){
//        ressourceService.deleteRessourcesById(id, httpSession);
//    }
//
//
//    @PostMapping("admin/ressources")
//    public void ajouterRessources(@RequestBody RessourceEntity ressourceEntity, HttpSession httpSession) {
//        ressourceService.ajouterRessources(ressourceEntity, httpSession);
//    }
//
//
//
//
//    @PutMapping("admin/ressources/{id}")
//    public ResponseEntity<?> editerRessources(@PathVariable Long id, @RequestBody RessourceDto ressourceDto, HttpSession httpSession) {
//        // Vérifier qu'un admin est connecté
//        UserEntity admin = (UserEntity) httpSession.getAttribute("id_admin");
//        if (admin != null) {
//            // Si l'utilisateur est un admin
//            // on cherche si la ressource existe
//            RessourceDto existingRessource = ressourceService.findById(id);
//            if (existingRessource != null) {
//                // Mettre à jour les champs de la ressource avec les nouvelles valeurs
//                existingRessource.setTitle(ressourceDto.getTitle());
//                existingRessource.setDescription(ressourceDto.getDescription());
//                // Continuez avec les autres champs...
//
//                // Enregistrer la ressource mise à jour
//                ressourceService.save(existingRessource);
//                return ResponseEntity.ok("Ressource mise à jour avec succès");
//            }
//
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aucun admin connecté. Veuillez vous connecter pour éditer une ressource.");
//    }*/
//
//
//
//
//
//}
//
//
//
