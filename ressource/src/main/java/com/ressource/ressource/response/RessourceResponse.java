package com.ressource.ressource.response;


import com.ressource.ressource.dto.RessourceDto;
import com.ressource.ressource.dto.UserDto;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class RessourceResponse  {

    HttpStatus status;

    String message;

    RessourceDto resource;

}
