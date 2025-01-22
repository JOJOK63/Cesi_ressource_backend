package com.ressource.ressource.controller;

import com.ressource.ressource.dto.LoginDto;
import com.ressource.ressource.dto.UserDto;
import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.security.JwtService;
import com.ressource.ressource.service.UserService;
import com.ressource.ressource.utils.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    RequestCache session;


    @PostMapping(path = "signup")
    public void signup(@RequestBody UserDto userDto) {

        log.info("Inscription");
        if (userDto.getRole() == null){
            userDto.setRole(Role.CITOYEN);
        }
        this.userService.inscription(userDto);
    }

    @PostMapping(path = "activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.userService.activation(activation);
    }

    @PostMapping(path = "login")
    public UserDto login(@RequestBody LoginDto loginDto) {
        UserDto user = null;
        final Authentication authenticate = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        if(authenticate.isAuthenticated()) {
            user = this.jwtService.generate(loginDto.getEmail());
        }

        System.out.println("User : " + user); // UserDto(id=3, name=Jean, password=$2a$10$4iK79VwlnJDXonT3k/qneebaAfcYxMyfAqbBEatOxbP9p.8D2l9U., email=jean@example.com, role=CITOYEN, actif=true, birthDate=2024-03-30T12:00, ressources=null, token=eyJhbGciOiJIUzI1NiJ9.eyJub20iOiJKZWFuIiwic3ViIjoiamVhbkBleGFtcGxlLmNvbSIsImV4cCI6MTcxNDgzNDQ5MH0.vcoMbHT4hG_yQUhy_KYZ84hm9WPjWeH4rs_qfuK_2gg)
        System.out.println("Role : " + user);
        return user;
    }

    @GetMapping(path = "disconnect")
    public String disconnect(){
        String message = "error";
        if (SecurityContext.isConnected()){
            SecurityContext.disconnect();
             message ="success";
        }
        return message;
    }

    @GetMapping("context")
    public String getContext(){
        return SecurityContext.getIdUser().toString() + SecurityContext.getName() + SecurityContext.getMail();
    }

}
