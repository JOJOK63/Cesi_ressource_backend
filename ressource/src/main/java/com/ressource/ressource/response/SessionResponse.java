package com.ressource.ressource.response;


import com.ressource.ressource.dto.UserDto;
import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class SessionResponse {

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
    UserDto user;

}
