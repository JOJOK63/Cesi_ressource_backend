package com.ressource.ressource.service.Impl;

import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.RessourceEntity;
import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.mapper.RessourceMapper;
import com.ressource.ressource.repositories.RessourceRepository;
import com.ressource.ressource.service.RessourceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RessourceServiceImpl implements RessourceService {

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private RessourceMapper ressourceMapper;

    @Override
    public List<RessourceDto> findAll(){
        List<RessourceEntity> ressourceEntityList = ressourceRepository.findAll();
        return ressourceMapper.convertToDtoList(ressourceEntityList);
    }

    @Override
    public RessourceDto findById(Long id){
        RessourceEntity ressourceEntity = ressourceRepository.findById(id).orElse(null);
        return ressourceMapper.convertToDto(ressourceEntity);
    }

    @Override
    public void save(RessourceDto ressourceDto){
        RessourceEntity ressourceEntity = ressourceMapper.convertToEntity(ressourceDto);
        ressourceRepository.save(ressourceEntity);
    }



    // Supprimer une ressource par l'Id
    @Override
    public void deletedById(Long id){
        ressourceRepository.deleteById(id);
    }

    @Override
    public List<RessourceDto> findByUserId(Long id) {
        RessourceEntity ressourceEntity = ressourceRepository.findById(id).orElse(null);
        return (List<RessourceDto>) ressourceMapper.convertToDto(ressourceEntity);
    }

    @Override
    public void deleteRessourcesById(Long id, HttpSession httpSession) {
        // Vérifie si l'utilisateur est connecté en tant qu'administrateur
        UserEntity admin = (UserEntity) httpSession.getAttribute("id_admin");
        if (admin != null && admin.getRole() == Role.ADMIN) {
            // Si l'utilisateur est un administrateur, supprimer l'entité correspondante
            ressourceRepository.deleteById(id);
        } else {
            // Si l'utilisateur n'est pas connecté en tant qu'administrateur ou n'existe pas, lancer une exception ou gérer autrement l'erreur
            throw new RuntimeException("ERREUR");
        }
    }

    @Override
    public ResponseEntity<String> ajouterRessources(RessourceEntity ressourceEntity, HttpSession httpSession) {
        try {
            // Vérifier qu'un admin est connecté
            UserEntity admin = (UserEntity) httpSession.getAttribute("id_admin");
            if (admin != null) {
                // Si l'utilisateur est un admin, il va pouvoir ajouter une ressource
                ressourceEntity.setUtilisateur(admin);
                ressourceRepository.save(ressourceEntity);
                return ResponseEntity.ok("Ressource ajouté avec succès par un admin");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aucun admin connecté lors de l'ajout d'une ressource");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Une erreur est survenue lors de l'ajout de la ressource.");
        }

    }






}
