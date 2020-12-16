package com.project.socializingApp.repository;

import com.project.socializingApp.model.RefreshToken;
import com.project.socializingApp.model.User;
import com.project.socializingApp.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepo  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}
