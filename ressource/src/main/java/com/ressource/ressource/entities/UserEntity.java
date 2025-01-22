package com.ressource.ressource.entities;

import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.enumeration.StatusPublication;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "utilisateur")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private boolean status = true;

    // Par défaut l'utilisateur est un citoyen
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role; // lien vers l'enum "Role"

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<RessourceEntity> ressource;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<CommentaryEntity> commentaires;

    private boolean actif;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }

}
