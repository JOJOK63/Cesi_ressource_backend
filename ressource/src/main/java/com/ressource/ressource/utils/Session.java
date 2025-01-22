package com.ressource.ressource.utils;

import com.ressource.ressource.enumeration.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class Session {

    private static HttpSession httpSession ;

    public Session(HttpSession session ,long id,String username,String email,Role role){

        httpSession = session;
        httpSession.setAttribute("isConnected",true);
        httpSession.setAttribute("id", id);
        httpSession.setAttribute("username",username);
        httpSession.setAttribute("email",email);
        httpSession.setAttribute("role",role);
    }

    public static long getIdUser(){
        return (long) httpSession.getAttribute("id");
    }
    public static String getName(){
        return (String) httpSession.getAttribute("username");
    }

    public static String getEmail(){
        return (String) httpSession.getAttribute("email");
    }

    public static Role getRole(){
        return (Role) httpSession.getAttribute("role");
    }

    public static boolean isConnected(){
        return (boolean) httpSession.getAttribute("isConnected");
    }

    public static String getSession(){
        return getIdUser()+" "+getName()+" "+getEmail()+" "+getRole();
    }

    public static void disconnected(){httpSession.invalidate();}

}
