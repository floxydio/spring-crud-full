package com.dio.springlearn.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.springlearn.models.AuthModels;
import com.dio.springlearn.repository.AuthRepo;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class AuthService {

    @Autowired
    private AuthRepo authRepo;

    public AuthModels signUp(AuthModels authModels) {
        String bcryptPassword = BCrypt.withDefaults().hashToString(10, authModels.getPassword().toCharArray());
        String createdAts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        authModels.setPassword(bcryptPassword);
        authModels.setCreatedAt(createdAts);
        
        return authRepo.save(authModels);
    }

    public AuthModels findByEmail(AuthModels authModels) {
        return authRepo.findUserByEmail(authModels.getEmail());
    }
    
}
