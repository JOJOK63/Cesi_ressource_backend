package com.ressource.ressource.dto;

import com.ressource.ressource.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginAdminDto {

    private String email;
    private String password;
    private Role role;

}
