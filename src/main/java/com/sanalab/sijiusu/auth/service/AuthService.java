package com.sanalab.sijiusu.auth.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.auth.data.Role;
import com.sanalab.sijiusu.auth.database.model.RefreshToken;
import com.sanalab.sijiusu.auth.database.model.User;
import com.sanalab.sijiusu.auth.database.repository.RefreshTokenRepository;
import com.sanalab.sijiusu.auth.database.repository.UserRepository;
import com.sanalab.sijiusu.core.component.HashEncoder;
import com.sanalab.sijiusu.core.service.Validator;
import com.sanalab.sijiusu.siji_admin.database.repository.AdminRepository;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import com.sanalab.sijiusu.siji_student.database.repository.StudentRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final HashEncoder hashEncoder;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final AdminRepository adminRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Validator validator;

    @Autowired
    public AuthService(
        JwtService jwtService,
        HashEncoder hashEncoder,
        UserRepository userRepository,
        StudentRepository studentRepository,
        LecturerRepository lecturerRepository,
        AdminRepository adminRepository,
        RefreshTokenRepository refreshTokenRepository,
        Validator validator
    ) {
        this.jwtService = jwtService;
        this.hashEncoder = hashEncoder;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.adminRepository = adminRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.validator = validator;
    }

    public record TokenPair(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken
    ) {}


    public TokenPair login(String identifier, String password) {
        if (identifier == null || identifier.isBlank() || password == null || password.isBlank()) {
            throw new BadCredentialsException("Identifier and password are required");
        }

        var identifierTrimmed = identifier.trim();

        var isEmail = validator.isEmail(identifierTrimmed);
        var isNim = validator.isNim(identifierTrimmed);
        var isNidn = validator.isNidn(identifierTrimmed);
        var isNip = validator.isNip(identifierTrimmed);

        if(!isEmail && !isNim && !isNidn && !isNip) throw new BadCredentialsException("Invalid credentials");

        User user;

        if(isEmail) {
            user = userRepository.findByEmail(identifierTrimmed).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        } else if(isNim) {
            user = studentRepository.findByNim(identifierTrimmed).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        } else if(isNidn) {
            user = lecturerRepository.findByNidn(identifierTrimmed).orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        } else {
            user = lecturerRepository.findByNip(identifierTrimmed)
                .map(lecturer -> (User) lecturer)
                .orElseGet(() ->
                    adminRepository.findByNip(identifierTrimmed)
                        .map(a -> (User) a)
                        .orElseThrow(() -> new BadCredentialsException("Invalid credentials"))
                );
        }

        if(!hashEncoder.matches(password, user.getPasswordHashed()))
            throw new BadCredentialsException("Invalid credentials");

        return storeAndGetToken(user.getId(), user.getRole(), null);
    }

    @Transactional
    public void logout(String refreshToken) {
        if(!jwtService.validateRefreshToken(refreshToken))
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");

        var userId = jwtService.getUserIdFromToken(refreshToken);

        var hashed = hashToken(refreshToken);
        refreshTokenRepository.findByUserIdAndHashedToken(userId, hashed).orElseThrow(() ->
            responseException(HttpStatus.UNAUTHORIZED, "Invalid refresh token")
        );

        refreshTokenRepository.deleteByUserIdAndHashedToken(userId, hashed);
    }

    @Transactional
    public TokenPair refresh(String refreshToken) {
        if(!jwtService.validateRefreshToken(refreshToken))
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");

        var userId = jwtService.getUserIdFromToken(refreshToken);
        var user = userRepository.findById(userId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "User not found")
        );

        String hashed = hashToken(refreshToken);
        refreshTokenRepository.findByUserIdAndHashedToken(user.getId(), hashed).orElseThrow(() ->
            responseException(HttpStatus.UNAUTHORIZED, "Invalid refresh token")
        );

        refreshTokenRepository.deleteByUserIdAndHashedToken(user.getId(), hashed);
        return storeAndGetToken(user.getId(), user.getRole(), refreshToken);
    }

    private TokenPair storeAndGetToken(
        Long userId,
        Role role,
        @Nullable String oldRefreshToken
    ) {
        var accessToken = jwtService.generateAccessToken(userId, role);
        var refreshToken = oldRefreshToken != null ? oldRefreshToken : jwtService.generateRefreshToken(userId, role);

        storeRefreshToken(userId, refreshToken);

        return new TokenPair(accessToken, refreshToken);
    }

    private void storeRefreshToken(Long userId, String refreshToken) {
        var hashed = hashToken(refreshToken);
        var expiryMs = jwtService.getRefreshTokenValidityMs();
        var expiresAt = Instant.now().plusMillis(expiryMs);

        var newRefreshToken = new RefreshToken();
        newRefreshToken.setUserId(userId);
        newRefreshToken.setCreatedAt(Instant.now());
        newRefreshToken.setExpiresAt(expiresAt);
        newRefreshToken.setHashedToken(hashed);

        refreshTokenRepository.save(newRefreshToken);
    }

    private static String hashToken(String token) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var hashBytes = digest.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
