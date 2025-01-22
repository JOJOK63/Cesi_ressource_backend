package com.ressource.ressource.response;


import com.ressource.ressource.dto.RessourceDto;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class RessourcesResponse {

    HttpStatus status;

    String message;

    List<RessourceDto> resources;

}
