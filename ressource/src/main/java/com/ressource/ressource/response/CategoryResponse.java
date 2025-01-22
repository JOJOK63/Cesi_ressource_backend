package com.ressource.ressource.response;

import com.ressource.ressource.dto.CategoryDto;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class CategoryResponse {

    HttpStatus status;
    String message;
    List<CategoryDto> category;

}

