package com.ressource.ressource.service.Impl;


import com.ressource.ressource.entities.UserEntity;
import com.ressource.ressource.entities.ValidationEntity;
import com.ressource.ressource.repositories.ValidationRepository;
import com.ressource.ressource.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@AllArgsConstructor
@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private ValidationRepository validationRepository;

//    private NotificationService notificationService;

    @Override
    public void enregistrer(UserEntity user) {
        ValidationEntity validation = new ValidationEntity();
        validation.setUser(user);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);
        this.validationRepository.save(validation);
//        this.notificationService.envoyer(validation);
    }

    @Override
    public ValidationEntity lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code);
    }
}