package com.ressource.ressource.dto;

import com.ressource.ressource.entities.RessourceEntity;
import com.ressource.ressource.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UserDto{

    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private Role role;

    private boolean actif = true ;

    private LocalDate birthDate;

    private List<RessourceEntity> ressources;

    private String token;

}
