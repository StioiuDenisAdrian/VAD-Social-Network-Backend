package com.project.socializingApp.controller;


import com.project.socializingApp.dataLayer.AuthResponse;
import com.project.socializingApp.dataLayer.Login;
import com.project.socializingApp.dataLayer.RefreshTokenData;
import com.project.socializingApp.dataLayer.RegisterData;
import com.project.socializingApp.model.RefreshToken;
import com.project.socializingApp.service.AutentificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class Autentification {

    private final AutentificationService autentificationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterData registerData) {
        try {
            autentificationService.signUp(registerData);
            return new ResponseEntity<>("User registered!", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("User taken!", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Login login) {
        return autentificationService.login(login);
    }

    @PostMapping("/refresh/token")
    public AuthResponse refreshTokens(@Valid @RequestBody RefreshTokenData refreshTokenData) {
        return autentificationService.refreshToken(refreshTokenData);
    }
}
