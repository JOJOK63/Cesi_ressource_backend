package com.ressource.ressource;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * A ajouter pour ne pas avoir d'erreur avant de faire une méthodes pour accepter les URLS externres
 * exclude = SecurityAutoConfiguration.class
 */


@SpringBootApplication()
public class RessourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RessourceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
