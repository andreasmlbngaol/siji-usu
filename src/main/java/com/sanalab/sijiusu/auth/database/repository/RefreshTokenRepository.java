package com.sanalab.sijiusu.auth.database.repository;

import com.sanalab.sijiusu.auth.database.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteAllByExpiresAtBefore(Instant timeBefore);
    Optional<RefreshToken> findByUserIdAndHashedToken(Long userId, String hashedToken);
    void deleteByUserIdAndHashedToken(Long userId, String hashedToken);

}
