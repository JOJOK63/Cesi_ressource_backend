package com.ressource.ressource.service.Impl;

import com.ressource.ressource.dto.LoginAdminDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.entities.ValidationEntity;
import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.mapper.UserMapper;
import com.ressource.ressource.repositories.UserRepository;
import com.ressource.ressource.response.UserResponse;
import com.ressource.ressource.service.UserService;
import com.ressource.ressource.service.ValidationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation du service user
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ValidationService validationService;
    /**
     * Repository user
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Mapper user
     */
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void activation(Map<String, String> activation) {
        ValidationEntity validationEntity = this.validationService.lireEnFonctionDuCode(activation.get("code"));
        if(Instant.now().isAfter(validationEntity.getExpiration())){
            throw  new RuntimeException("Votre code a expiré");
        }
        UserEntity utilisateurActiver = this.userRepository.findById(validationEntity.getUser().getId()).get();
        utilisateurActiver.setActif(true);
        this.userRepository.save(utilisateurActiver);
    }
    @Override
    public void inscription(UserDto user) {

        if(!user.getEmail().contains("@")) {
            throw  new RuntimeException("Votre mail invalide");
        }
        if(!user.getEmail().contains(".")) {
            throw  new RuntimeException("Votre mail invalide");
        }

        UserEntity utilisateurOptional = this.userRepository.findByEmail(user.getEmail());
        if(utilisateurOptional != null) {
            throw  new RuntimeException("Votre mail est déjà utilisé");
        }
        String mdpCrypte = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(mdpCrypte);

        UserEntity userEntity = this.userRepository.save(this.userMapper.convertToEntity(user));
//        this.validationService.enregistrer(userEntity);
    }
    /**
     * Renvoit la liste de tous les utilisateurs
     * @return Liste d'utilisateur
     */
    @Override
    public List<UserDto> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return userEntityList.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un utilisateur à partir de son id.
     * @param id Id de l'utilisateur
     * @return Un utilisateur
     */
    @Override
    public UserDto findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return userMapper.convertToDto(userEntity);
    }

    @Override
    public UserDto findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userMapper.convertToDto(userEntity);
    }

    /**
     * Ajoute un utilisateur
     * @param userDto L'utilisateur à ajouter
     */
    @Override
    public UserDto save(UserDto userDto) {
        UserEntity userEntity = userMapper.convertToEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    /**
     * Supprime un utilisateur à partir de son id
     * @param id Id de l'utilisateur
     */
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<String> deleteAdminById(Long id, HttpSession httpSession) {
        // Vérifier si l'utilisateur est connecté en tant qu'administrateur
        UserEntity admin = (UserEntity) httpSession.getAttribute("id_admin");
        if (admin != null && admin.getRole() == Role.ADMIN) {
            // Si l'utilisateur est un administrateur, supprimer l'entité correspondante
            userRepository.deleteById(id);
            return ResponseEntity.ok("Entité supprimée avec succès.");
        } else {
            // Si l'utilisateur n'est pas connecté en tant qu'administrateur ou n'existe pas, retourner un message d'erreur avec le code d'erreur approprié
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'avez pas les autorisations nécessaires pour effectuer cette action.");
        }
    }


    // A AMELIORER
    private ResponseEntity<?> emailValide(String email){
        // Vérifier que le mail est valide
        // Si l'email ne contient pas de "@" l'email est invalide
        if (!email.contains("@")){
            return ResponseEntity.ok("Votre email est invalide");
        }
        // Si l'email ne contient pas de "." l'email est invalide
        if (!email.contains(".")){
            return ResponseEntity.ok("Votre email est invalide");
        }
        return ResponseEntity.ok("Votre email est invalide");
    }


    private ResponseEntity<?> verifierEmailUtilise(String email){
        // Vérifier que l'utilisateur n'est pas déjà inscrit
        UserEntity userExisting = userRepository.findByEmail(email);
        if (userExisting != null){
            return ResponseEntity.ok("Votre email est invalide");

        }
        return ResponseEntity.ok("Votre email est invalide");
    }

    private void crypterPassword(UserDto userDto){
        // Crypter le password
        UserEntity userEntity = userMapper.convertToEntity(userDto);
        String passwordCrypte = this.passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordCrypte);

        // Enregistrer l'utilisateur dans la BDD
        userRepository.save(userEntity);
    }



//    public void inscription(UserDto userDto) {
//
//        // Utiliser les 3 méthodes ci-dessus
//        emailValide(userDto.getEmail());
//        verifierEmailUtilise(userDto.getEmail());
//        crypterPassword(userDto);
//    }

    @Override
    public UserDto loginUser(UserDto userDto) {
//
//        String message = "";
//        UserEntity utilisateur1 = userRepository.findByEmail(loginDto.getEmail());
//
//        if (utilisateur1 != null) {
//            String password = loginDto.getPassword();
//            String encodedPassword = utilisateur1.getPassword();
//
//            boolean passwordTrue = passwordEncoder.matches(password, encodedPassword);
//
//            if (passwordTrue) {
//                httpSession.setAttribute("id_user", utilisateur1);
//                Optional<UserEntity> userEntity = userRepository.findOneByEmailAndPassword(loginDto.getEmail(), encodedPassword);
//                if (userEntity.isPresent()){
//                    return new LoginResponse("Login success", true);
//                } else {
//                    return new LoginResponse("Login failed", false);
//                }
//            } else {
//                return new LoginResponse("Password Not Match", false,null);
//            }
//        } else {
//            return new LoginResponse("Email not exists", false,null);
//        }
        return null;
    }



    @Override
    public UserResponse loginAdmin(LoginAdminDto loginAdminDto, HttpSession httpSession) {
//
//        String message = "";
//        UserEntity admin = userRepository.findByEmail(loginAdminDto.getEmail());
//
//        if (admin != null) {
//            String password = loginAdminDto.getPassword();
//            String encodedPassword = admin.getPassword();
//
//            Boolean passwordTrue = passwordEncoder.matches(password, encodedPassword);
//
//            if (passwordTrue) {
//                httpSession.setAttribute("id_admin", admin);
//                Optional<UserEntity> adminEntity = userRepository.findOneByEmailAndPassword(loginAdminDto.getEmail(), encodedPassword);
//                Role userRole = admin.getRole();
//                if (userRole == Role.ADMIN) {
//                    if (adminEntity.isPresent()) {
//                        return new UserResponse("Login success", true,null);
//                    }
//                    else {
//                        return new UserResponse("Login failed", false,null);
//                    }
//                }
//                else {
//                    return new UserResponse("Login failed is not Admin account", false,null);
//                }
//            }
//            else {
//                return new UserResponse("Password Not Match", false,null);
//            }
//        }
//        else {
//            return new UserResponse("Email not exists", false,null);
//        }
    return null;
    }


    // -----------------------------

    // Désactiver un compte

    @Override
    public ResponseEntity<?> desactiverCompte(Long id, HttpSession httpSession){
        // Vérifie si l'utilisateur est connecté en tant qu'administrateur
        UserEntity admin = (UserEntity) httpSession.getAttribute("id_admin");
        if (admin != null && admin.getRole() == Role.ADMIN) {
            Optional<UserEntity> userEntityOptional = userRepository.findById(id);
            if (userEntityOptional.isPresent()){
                UserEntity userEntity = userEntityOptional.get();
                userEntity.setStatus(false);
                userRepository.save(userEntity);
            } else {
                return ResponseEntity.ok("Utilisateur non trouvé avec l'ID: " + id);
            }
        } else {
            return ResponseEntity.ok("Aucun admin connecté");
        }
            return null;
    }


    /*@Override
    public List<UserDto> findAllAdmins() {
        List<UserEntity> adminEntityList = userRepository.findByRole(Role.ADMIN);
        return adminEntityList.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }*/
}
