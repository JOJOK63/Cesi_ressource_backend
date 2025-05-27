//package com.ressource.ressource;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//public class Test {
//
//    public static void main(String[] args) {
//        String password = "motdepasse";
//        String password1 = "motdepasse";
//        System.out.println(password);
//
//        // Hachage du mot de passe
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String hashedPassword = encoder.encode(password);
//        String hashedPassword1 = encoder.encode(password1);
//
//        System.out.println("Mot de passe haché : " + hashedPassword);
//        System.out.println("Mot de passe haché : " + hashedPassword1);
//
//        // Simulation de la tentative de connexion
//        String attemptedPassword = "motdepasse";
//        System.out.println(attemptedPassword);
//        String attemptedPassword1 = "motdepasse";
//        System.out.println(attemptedPassword1);
//
//        // Comparaison des mots de passe hachés
//        if (encoder.matches(attemptedPassword, hashedPassword)) {
//            System.out.println("Authentification réussie !");
//        } else {
//            System.out.println("Échec de l'authentification !");
//        }
//        // Comparaison des mots de passe hachés
//        if (encoder.matches(attemptedPassword1, hashedPassword1)) {
//            System.out.println("Authentification réussie !");
//        } else {
//            System.out.println("Échec de l'authentification !");
//        }
//    }
//}
