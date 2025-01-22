package com.ressource.ressource;

import java.sql.Connection;
import java.sql.DriverManager;

/*
ON TESTE LA CONNEXION A LA BASE DE DONNEES HEBERGEE
* */

public class TestConnection {

    public static void main(String[] args) {
        String jdbcUrl="jdbc:mysql://mysql-projetcesi.alwaysdata.net/projetcesi_bdd?useSSL=false&serverTimezone=UTC";

        String user="347601";
        String password = "eK#u@gP#M35wM-Q";
        try {
            System.out.println("Connecting to database " + jdbcUrl);
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println("Connection successfull!!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}




