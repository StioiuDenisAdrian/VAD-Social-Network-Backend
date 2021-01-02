package com.project.socializingApp.service;

import com.project.socializingApp.dataLayer.AuthResponse;
import com.project.socializingApp.dataLayer.Login;
import com.project.socializingApp.dataLayer.RefreshTokenData;
import com.project.socializingApp.dataLayer.RegisterData;
import com.project.socializingApp.model.*;
import com.project.socializingApp.repository.PhotoRepo;
import com.project.socializingApp.repository.UserRepo;
import com.project.socializingApp.repository.VerificationTokenRepo;
import com.project.socializingApp.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;


import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@Service
public class AutentificationService {

    private final BCryptPasswordEncoder encoder;
    private final UserRepo userRepo;
    private final VerificationTokenRepo verificationTokenRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final PhotoRepo photoRepo;

    @Transactional
    public void signUp (RegisterData registerData ) throws Exception {
        try{
            userRepo.findByUserName(registerData.getUserName());
        }catch ( Exception ex){
            throw new Exception("Not ok");
        }
        User user = new User();
        user.setUserName(registerData.getUserName());
        user.setEmail(registerData.getEmail());
        user.setPassword(encoder.encode(registerData.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(true); // for email verification set it to false
        user.setRecommendation(photoRepo.findAllByOrderByIdDesc());
        user.setRecomIndex(0);
        user.setFriends(new ArrayList<>());
        userRepo.save(user);
        generateVerification(user);
    }

    private String generateVerification(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepo.save(verificationToken);
        return token;
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUserName();
        User user = userRepo.findByUserName(username).orElseThrow(() -> new MyException("User not found with name - " + username));
        user.setEnabled(true);
        userRepo.save(user);
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepo.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new MyException("Invalid Token")));
    }

    public AuthResponse login(Login loginRequest) {
        Optional<User> user = userRepo.findByUserName(loginRequest.getUserName());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(loginRequest.getUserName())
                .email(user.get().getEmail())
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenData refreshTokenData) {
        refreshTokenService.validateRefreshToken(refreshTokenData.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenData.getUserName());
        return AuthResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenData.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .userName(refreshTokenData.getUserName())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
