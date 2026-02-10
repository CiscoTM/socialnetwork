package com.example.socialnetwork.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;

    private final long accessTokenValidityMillis = 15 * 60 * 1000;          // 15 min
    private final long refreshTokenValidityMillis = 7L * 24 * 60 * 60 * 1000; // 7 días

    // Producción
    public JwtTokenProvider() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Tests (secreto fijo)
    public JwtTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(accessTokenValidityMillis)))
                .claim("type", "ACCESS")
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(refreshTokenValidityMillis)))
                .claim("type", "REFRESH")
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(parse(token).getBody().get("type", String.class));
    }

    public String getUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    public String validateAndGetSubject(String token) {
        if (!validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        return getUsername(token);
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
