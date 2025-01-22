package com.ressource.ressource.utils;

import com.ressource.ressource.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContext {

    public static boolean isConnected(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    public static Long getIdUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isConnected() && authentication.getPrincipal() instanceof UserEntity){
            return ((UserEntity) authentication.getPrincipal()).getId();
        }
        return null;
    }

    public static String getName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isConnected() && authentication.getPrincipal() instanceof UserEntity){
            return ((UserEntity) authentication.getPrincipal()).getFirstName();
        }
        return null;
    }

    public static String getMail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isConnected() && authentication.getPrincipal() instanceof UserEntity){
            return ((UserEntity) authentication.getPrincipal()).getEmail();
        }
        return null;
    }

    public static void disconnect() {
        SecurityContextHolder.clearContext();
    }
}