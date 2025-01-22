package com.ressource.ressource.response;

import com.ressource.ressource.dto.CommentaryDto;
import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.dto.UserDto;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CommentaryResponse {

    HttpStatus status;

    String message;

    RessourceDto ressource;

    UserDto user;


}
