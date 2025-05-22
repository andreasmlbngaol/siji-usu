package com.sanalab.sijiusu.auth.service;

import com.sanalab.sijiusu.auth.data.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey secretKey;
    private final long accessTokenValidityMs = 15 * 60 * 1000L; // 15 minutes
    private final long refreshTokenValidityMs = 24 * 60 * 60 * 1000L; // 1 hari

    public long getAccessTokenValidityMs() {
        return accessTokenValidityMs;
    }

    public long getRefreshTokenValidityMs() {
        return refreshTokenValidityMs;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    private String generateToken(
        long userId,
        String type,
        Role role,
        long expiry
    ) {
        var now = new Date();
        var expiration = new Date(now.getTime() + expiry);

        return Jwts.builder()
            .subject(String.valueOf(userId))
            .claim("type", type)
            .claim("role", role)
            .issuedAt(now)
            .expiration(expiration)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact();
    }

    public String generateAccessToken(Long userId, Role role) {
        return generateToken(
            userId,
            "access",
            role,
            accessTokenValidityMs
        );
    }

    public String generateRefreshToken(Long userId, Role role) {
        return generateToken(
            userId,
            "refresh",
            role,
            refreshTokenValidityMs
        );
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, "access");
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, "refresh");
    }

    private boolean validateToken(String token, String type) {
        Claims claims = parseAllClaims(token);
        if(claims == null) return false;

        String tokenType;
        try {
            tokenType = (String) claims.get("type");
        } catch (Exception ignored) {
            return false;
        }

        return tokenType.equals(type);
    }

    private Claims parseAllClaims(String token) {
        var rawToken = token;
        if(token.startsWith("Bearer ")) {
            rawToken = token.replace("Bearer ", "");
        }

        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .getPayload();
        } catch (Exception ignored) {
            return null;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = parseAllClaims(token);
        if(claims == null) throw responseException(401, "Invalid token");
        try {
            return Long.parseLong(claims.getSubject());
        } catch (Exception ignored) {
            return 0L;
        }
    }
}
