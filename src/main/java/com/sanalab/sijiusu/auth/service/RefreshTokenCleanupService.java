package com.sanalab.sijiusu.auth.service;

import com.sanalab.sijiusu.auth.database.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenCleanupService {
    private final RefreshTokenRepository repository;

    @Autowired
    public RefreshTokenCleanupService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000) // every one hour
    public void cleanupExpiredTokens() {
        var now =  Instant.now();
        repository.deleteAllByExpiresAtBefore(now);
    }
}
