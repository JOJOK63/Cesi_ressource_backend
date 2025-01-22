package com.ressource.ressource.response;


import com.ressource.ressource.dto.UserDto;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Réponse renvoyé
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class UsersResponse {

    /**
     * Code http renvoyé
     */
    HttpStatus codeHttp;

    /**
     * Méssage renvoyé de la requète
     */
    String message;

    /**
     * LoginDto
     */
    List<UserDto> users;

}
