package com.ressource.ressource.repositories;

import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Accès à la table user
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {


//    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
//    UserEntity findByEmail(String email);
    UserEntity findByEmail(String email);

    UserEntity findOneByEmailAndPassword(String email, String encodedPassword);

   UserEntity findByRole(Role role);
}
